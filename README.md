#[RES](http://redditenhancementsuite.com/) Backup Assistant

Backup functionality is available and tested on OS X and Linux (with Oracle Java 7), however some features are a bit iffy. Here is a little TODO list that
will lay them out as well as allow me a list of high priority items that shouldn't be too difficult to fix.

-------------------

##Browser/OS Status (as of V0.3.0)
####Mac (Tested on 10.9.3)
* Firefox - Working
* Chrome - Working
* Chromium - Working
* Opera - Working
* Safari - Working

####Linux (Tested on Ubuntu)
* Firefox - Working
* Chromium - Working
* Chrome - Working
* Opera - Working

####Windows
* Chrome - Working (Big thanks to [/u/Trapt45](http://www.reddit.com/user/Trapt45) for testing)

-------------------------

###TODO (as of V0.3.0)
* Fix behavior when the RES-Backups folder already exists in the users home dir. As of right now you will be prompted to rename it to RES-Backups-OLD.

* Find out more about the .localstorage-journal file and its purpose (and then if it should be backed-up as well).

* Convert alerts on stdout to alert window pop-ups or something similar

----------------------

#####What: 
*   The application searches for RES (Reddit Ehancement Suite) installs on your computer
    and make a copy of them in a folder in your user's home directory.

#####Why: 
*   Backups are good, and especially right now for firefox users because firefox has been deleting res settings on reset.

#####Who:
*   This script currently only works for users running Mac OS X. I am currently working on re-writing into a cross-platform
version.

#####How:
*   The application works by checking the location of many different locations different browsers use to store your RES settings.  Then if
the file is found it is added to a backup file which is stored at the location you specify.

* To run it simply download the latest [release](https://github.com/walshie4/backup-RES-Settings/releases) and double click it. 
Then click `Detect RES Installs`. Then check the installs you want to make a backup of and click `Make Backup`. 
A copy of the selected config files will be stored in your user's home directory in a folder called RES-Backups.

#####Where:
*   Check the releases for the latest working version, or download the latest commit (but don't expect it to be bug-less if you do).

