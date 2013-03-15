cat ht1.vi > src/com/soward/db/DB.java
ant remove
ant
scp dist/HT.war root@192.168.1.100:/home/ssoward/tools/wars/
ant install
./ht2.sh
