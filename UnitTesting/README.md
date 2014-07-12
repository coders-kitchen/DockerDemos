UnitTesting
===========

This demo shows, how to utilize docker for having a fresh and new database for unit testing.

Requirements
------------

 * JDK7+
 * postgres client

Run
---

Change into UnitTesting directory and execute 

    demoStart.sh
    
This will 
 
  * create the unittest/postgresql image if necessary
  * execute the gradle tasks
  * restart the postgresql container
  * select everything from the book table
  * stop the postgresql container
