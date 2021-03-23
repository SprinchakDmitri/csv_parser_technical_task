# csv_parser_technical_task

1. Download sqlite3 for your OS by following link "https://www.sqlite.org/download.html"

2. Create a new database with command sqlite3 <database_name>.db in  command prompt.

3. Create a table with following script:
  CREATE TABLE persons (
    first_name varchar(50),
    last_name varchar (50),
    user_name varchar (50),
    age integer,
    email varchar(50),
    city varchar(50),
    phone_number varchar(30),
    job_title varchar(50),
    weight real,
    height real
    );

4. Find the persons.csv file at the root of the project.
    
5. Run application and follow the instructions in the command line.   
  1) Enter the absolute path to the database that you created in step 1.
  2) Enter the absolute path to the persons.csv from 4 step.
  3) Enter the table name from 3 step to update. By default, table name is "persons".
    
The 2 and 3 lines in persons.csv are corrupted, and they will be added to the bad-data file.
In the 4 line example with comma in double quotes.