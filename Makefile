dist: RESBackup.class RESBackupGUI.class
	jar cvfm RES-Backup-Assistant.jar MANIFEST.MF *.class

RESBackup.class:
	javac RESBackup.java

RESBackupGUI.class:
	javac RESBackupGUI.java

test: clean RESBackup.class RESBackupGUI.class
	java RESBackupController

clean:
	rm -f *.class

push:
	git push origin master

