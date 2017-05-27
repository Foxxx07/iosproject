# Configuration des serveurs

### Règles iptables
Des règles iptables on été mise en place sur les serveurs physique, afin que les services puissent dialoguer entre eux via des ports filtré, donc les accès sont contrôlé.

Ces règles sont inscritent dans le script `apply_rules.sh` .

#### apply_rules.sh
Ce script applique toutes les règles iptables au système.

Ce script est daemonisé dans `/usr/lib/systemd/system/apply_rules.service` :
```
[Unit]
Description=iptables customs rules

[Service]
Type=oneshot
ExecStart=/root/apply_rules.sh
RemainAfterExit=yes

[Install]
WantedBy=multi-user.target
```

##### Règles node DB
Fichier `/root/apply_rules.sh` :
```bash
#!/bin/bash

iptables="iptables -v"

$iptables -F
$iptables -X

# Current conn
$iptables -A INPUT -m conntrack --ctstate ESTABLISHED -j ACCEPT

# SSH
$iptables -A INPUT -p tcp --dport 2213 -s 92.169.219.170/32 -j ACCEPT

$iptables -A INPUT -p tcp --dport 2213 -j DROP

# MySQL Client
$iptables -A INPUT -p tcp --dport 3306 -s 10.0.0.0/8 -j ACCEPT
#$iptables -A INPUT -p tcp --dport 3306 -s 0.0.0.0/32 -j ACCEPT

$iptables -A INPUT -p tcp --dport 3306 -j DROP

# Galera Cluster Sync
$iptables -A INPUT -p tcp --dport 4567 -s 10.0.0.0/8 -j ACCEPT

$iptables -A INPUT -p tcp --dport 4567 -j DROP

# lo
$iptables -I INPUT -i lo -j ACCEPT

exit 0
```

## MariaDB & Galera Cluster
La configuration principale de MariaDB se trouve dans `/etc/my.cnf.d/server.cnf`.<br>
Galera Cluster se configure à la section `[galera]` :
```
[mysqld]
innodb_ft_min_token_size=2
event_scheduler=ON

[galera]
wsrep_on=ON
wsrep_provider=/usr/lib64/galera/libgalera_smm.so
wsrep_provider_options="pc.wait_prim=no"
wsrep_cluster_address="gcomm://255b4893-9e3f-498d-9758-da00697d0352.priv.cloud.scaleway.com,3d378140-354a-4691-8451-a9fb307e4931.priv.cloud.scaleway.com,ac13c5cb-b51b-4a6c-9b77-f418ac9e7a2b.priv.cloud.scaleway.com"
wsrep_cluster_name=25e135c9d45a456720ee4e31cc98c2ac
#wsrep_node_name=dbnodeN
wsrep_node_address=255b4893-9e3f-498d-9758-da00697d0352.priv.cloud.scaleway.com
wsrep_sst_auth=user:password
wsrep_sst_method=xtrabackup-v2
binlog_format=row
default_storage_engine=InnoDB
innodb_autoinc_lock_mode=2
bind-address=0.0.0.0
port=3306
```

**Descriptions:**

| propriété | exemple | description |
| --------- | ------- | ----------- |
| *bind-address* | 0.0.0.0 | adresse IP sur laquel écouter. |
| *binlog_format* | row | Le format (méthode) de réplication. |
| *default_storage_engine* | InnoDB | Le moteur de stockage par défaut. |
| *event_scheduler* | ON | État du contrôler d'événements. |
| *innodb_autoinc_lock_mode* | 2 | Mode de `LOCK` lors d'une auto-incrémentation. 2 pour le mode "Interleaved", utilisé par Galera en complément de `--binlog_format=ROW` . |
| *innodb_ft_min_token_size* | 2 | Taille minimum d'un mot stocker via un index FULLTEXT. exemple: "My" (prénom). |
| *port* | 3306 | Le port d'écoute du serveur. |
| *wsrep_on* | ON | État de la réplication des transactions entre noeuds d'un cluster. |
| *wsrep_provider* | /usr/lib64/galera/libgalera_smm.so | Path de la librairie de `wsrep` . |
| *wsrep_provider_options* | "pc.wait_prim=no" | Options à passer à `wsrep` . |
| *wsrep_cluster_address* | "gcomm://< node1 >, < node2 >, < nodeN >" | Adresse des noeuds du cluster, avec lesquels se synchroniser. |
| *wsrep_cluster_name* | imapcontacts | Le nom interne du cluster, partagé et contrôlé par les noeuds. |
| *wsrep_node_name* | dbnodeN | Le nom du node au sein du cluster. |
| *wsrep_node_address* | 1.2.3.4 | L'adresse du noeud. |
| *wsrep_sst_auth* | user:password | Authentification pour xtrabackup-v2. |
| *wsrep_sst_method* | xtrabackup-v2 | Méthode de transfert de l'état des snapshots ("state snapshot transfer" = "sst"). |

#### MaxScale & Read/Write Splitting
MaxScale permet de monitorer les nodes du cluster, et de définir un Master et des Slaves.<br>
Il gère aussi l'état des synchronisation entre nodes via Galera Cluster.

Voici la configuration de MaxScale `/etc/maxscale.cnf` :
```
[maxscale]
threads=2

[Splitter Service]
type=service
router=readwritesplit
servers=db1, db2, db3

[Splitter Listener]
type=listener
service=Splitter Service
protocol=MySQLClient
address=0.0.0.0
port=3306

[db1]
type=server
address=127.0.0.1
port=3307
protocol=MySQLBackend

[db2]
type=server
address=3d378140-354a-4691-8451-a9fb307e4931.priv.cloud.scaleway.com
port=3306
protocol=MySQLBackend

[db3]
type=server
address=ac13c5cb-b51b-4a6c-9b77-f418ac9e7a2b.priv.cloud.scaleway.com
port=3306
protocol=MySQLBackend

[Galera Monitor]
type=monitor
module=galeramon
servers=db1, db2, db3
disable_master_failback=1

[CLI]
type=service
router=cli

[CLI Listener]
type=listener
service=CLI
protocol=maxscaled
address=127.0.0.1
port=6603
```

Nous pouvons voir qu'il y a:
* un "Service" `Splitter Service`, qui est un router de type `readwritesplit`, permettant de router les requêtes de lecture vers les Slaves et les requêtes d'écriture vers le Master.
* un "Listener" `Splitter Listener`, qui un processus d'écoute, bindant sur le port 3306 en TCP, et qui redirige les demandes vers le précédent service `Splitter Service`.
* db1, db2, db3 sont configuré en tant que "nodes", `MySQLBackend`.
* un "Monitor" `Galera Monitor`, qui gère le monitoring des nodes.
* `CLI`  qui est un service de controle de MaxScale en console.

Une fois touts les processus lancé, voici la liste des services TCP:
```bash
# lsof -Pni
COMMAND     PID         USER   FD   TYPE  DEVICE SIZE/OFF NODE NAME
mysqld   29894        mysql   11u  IPv4 115847      0t0  TCP *:4567 (LISTEN)
mysqld   29894        mysql   12u  IPv4 115848      0t0  TCP 10.2.169.215:59312->10.2.15.7:4567 (ESTABLISHED)
mysqld   29894        mysql   13u  IPv4 118093      0t0  TCP 127.0.0.1:3307->127.0.0.1:34374 (ESTABLISHED)
mysqld   29894        mysql   14u  IPv4 115850      0t0  TCP 10.2.169.215:40802->10.2.32.23:4567 (ESTABLISHED)
mysqld   29894        mysql   15u  IPv4 115851      0t0  TCP 10.2.169.215:4567->10.2.169.215:48444 (CLOSE_WAIT)
mysqld   29894        mysql   33u  IPv4 115864      0t0  TCP 127.0.0.1:3307 (LISTEN)
maxscale 30176     maxscale    9u  IPv4 118092      0t0  TCP 127.0.0.1:34374->127.0.0.1:3307 (ESTABLISHED)
maxscale 30176     maxscale   10u  IPv4 118097      0t0  TCP 10.2.169.215:45424->10.2.15.7:3306 (ESTABLISHED)
maxscale 30176     maxscale   11u  IPv4 118099      0t0  TCP 10.2.169.215:39118->10.2.32.23:3306 (ESTABLISHED)
maxscale 30176     maxscale   13u  IPv4 118108      0t0  TCP *:3306 (LISTEN)
maxscale 30176     maxscale   14u  IPv4 118109      0t0  TCP 127.0.0.1:6603 (LISTEN)
```

Nous apperçevons que MaxScale:
* bind sur `127.0.0.1:6603` en tant qu'interface de gestion (service "CLI").
* bind sur `:3306` en tant que serveur d'entrée de connection (protocole "MySQLClient"), pour ensuite faire le "split".
* est connecté à MariaDB Server sur le port `:3307` en tant que "MySQLBackend".
* est connecté aux autres nodes sur le port `:3306` en tant que "MySQLBackend".

Une fois connecté à MaxScale via localhost:6603, nous pouvons voir l'état des nodes (suivi par "Galera Monitor"):
```bash
MaxScale> list servers
Servers.
-------------------+-----------------+-------+-------------+--------------------
Server             | Address         | Port  | Connections | Status              
-------------------+-----------------+-------+-------------+--------------------
db1                | 127.0.0.1       |  3307 |           0 | Master, Synced, Running
db2                | 3d378140-354a-4691-8451-a9fb307e4931.priv.cloud.scaleway.com |  3306 |           0 | Slave, Synced, Running
db3                | ac13c5cb-b51b-4a6c-9b77-f418ac9e7a2b.priv.cloud.scaleway.com |  3306 |           0 | Slave, Synced, Running
-------------------+-----------------+-------+-------------+--------------------
MaxScale> show servers
Server 0x636a90 (db1)
	Server:                              127.0.0.1
	Status:                              Master, Synced, Running
	Protocol:                            MySQLBackend
	Port:                                3307
	Server Version:                      10.1.21-MariaDB
	Node Id:                             0
	Master Id:                           -1
	Slave Ids:                           
	Repl Depth:                          0
	Number of connections:               0
	Current no. of conns:                0
	Current no. of operations:           0
Server 0x635d40 (db2)
	Server:                              3d378140-354a-4691-8451-a9fb307e4931.priv.cloud.scaleway.com
	Status:                              Slave, Synced, Running
	Protocol:                            MySQLBackend
	Port:                                3306
	Server Version:                      10.1.21-MariaDB
	Node Id:                             1
	Master Id:                           -1
	Slave Ids:                           
	Repl Depth:                          0
	Number of connections:               0
	Current no. of conns:                0
	Current no. of operations:           0
Server 0x635070 (db3)
	Server:                              ac13c5cb-b51b-4a6c-9b77-f418ac9e7a2b.priv.cloud.scaleway.com
	Status:                              Slave, Synced, Running
	Protocol:                            MySQLBackend
	Port:                                3306
	Server Version:                      10.1.21-MariaDB
	Node Id:                             2
	Master Id:                           -1
	Slave Ids:                           
	Repl Depth:                          0
	Number of connections:               0
	Current no. of conns:                0
	Current no. of operations:           0
```

Voir l'état du moniteur lui même:
```bash
MaxScale> list monitors
---------------------+---------------------
Monitor              | Status
---------------------+---------------------
Galera Monitor       | Running
---------------------+---------------------
MaxScale> show monitors
Monitor:           0x6395b0
Name:              Galera Monitor
State:             Running
Sampling interval: 10000 milliseconds
Connect Timeout:   3 seconds
Read Timeout:      1 seconds
Write Timeout:     2 seconds
Monitored servers: [127.0.0.1]:3307, [3d378140-354a-4691-8451-a9fb307e4931.priv.cloud.scaleway.com]:3306, [ac13c5cb-b51b-4a6c-9b77-f418ac9e7a2b.priv.cloud.scaleway.com]:3306
Master Failback:	off
Available when Donor:	off
Master Role Setting Disabled:	off
Set wsrep_sst_donor node list:	off
```

Voir l'état du service Splitter:
```bash
MaxScale> list services
Services.
--------------------------+-------------------+--------+----------------+-------------------
Service Name              | Router Module     | #Users | Total Sessions | Backend databases
--------------------------+-------------------+--------+----------------+-------------------
Splitter Service          | readwritesplit    |      1 |              1 | db1, db2, db3
CLI                       | cli               |      2 |              3 |
--------------------------+-------------------+--------+----------------+-------------------
MaxScale> show services
Service:                             Splitter Service
	Router:                              readwritesplit
	State:                               Started

	use_sql_variables_in:      all
	slave_selection_criteria:  LEAST_CURRENT_OPERATIONS
	master_failure_mode:       fail_instantly
	max_slave_replication_lag: -1
	retry_failed_reads:        true
	strict_multi_stmt:         true
	disable_sescmd_history:    true
	max_sescmd_history:        0
	master_accept_reads:       false

	Number of router sessions:           	0
	Current no. of router sessions:      	1
	Number of queries forwarded:          	0
	Number of queries forwarded to master:	0 (0.00%)
	Number of queries forwarded to slave: 	0 (0.00%)
	Number of queries forwarded to all:   	0 (0.00%)
	Started:                             Fri May 26 09:56:04 2017
	Root user access:                    Disabled
	Backend databases:
		[127.0.0.1]:3307    Protocol: MySQLBackend    Name: db1
		[3d378140-354a-4691-8451-a9fb307e4931.priv.cloud.scaleway.com]:3306    Protocol: MySQLBackend    Name: db2
		[ac13c5cb-b51b-4a6c-9b77-f418ac9e7a2b.priv.cloud.scaleway.com]:3306    Protocol: MySQLBackend    Name: db3
	Total connections:                   1
	Currently connected:                 1
	Service:                             CLI
	Router:                              cli
	State:                               Started
	Started:                             Fri May 26 09:56:04 2017
	Root user access:                    Disabled
	Backend databases:
	Total connections:                   3
	Currently connected:                 2
```

Voir l'état des Listeners:
```bash
MaxScale> list listeners
Listeners.
---------------------+---------------------+--------------------+-----------------+-------+--------
Name                 | Service Name        | Protocol Module    | Address         | Port  | State
---------------------+---------------------+--------------------+-----------------+-------+--------
Splitter Listener    | Splitter Service    | MySQLClient        | 0.0.0.0         |  3306 | Running
CLI Listener         | CLI                 | maxscaled          | 127.0.0.1       |  6603 | Running
---------------------+---------------------+--------------------+-----------------+-------+--------
```

 # Memcached
 Une instance Memcached est configuré via `/etc/sysconfig/memcached` :
 ```plain
PORT="11211"
USER="memcached"
MAXCONN="1024"
CACHESIZE="64"
OPTIONS="-s /var/run/memcached/memcached.sock -a 777 -P /var/run/memcached/memcached.pid"
 ```

 Et le daemon dans `/usr/lib/systemd/system/memcached.service` :
 ```plain
[Unit]
Description=Memcached
After=network.target

[Service]
Type=simple
PIDFile=/var/run/memcached/memcached.pid
EnvironmentFile=-/etc/sysconfig/memcached
ExecStart=/usr/bin/memcached -u $USER -p $PORT -m $CACHESIZE -c $MAXCONN $OPTIONS

[Install]
WantedBy=multi-user.target
 ```
