pastiesbin
----------
----------

pastiesbin is a commandline application allowing for the simple pasting of code
to pastebin given some file to read, which can be helpful for those using vim that
need to paste longer code.  It also eliminates having to copy and paste code directly
into pastebin from a text editor or IDE.  A range of line numbers to paste from a given
file is also supported.

Installation:
-------------
-------------
    *Note: This is compiled with scala 2.11.1
    
    Start sbt from the main directory:
        sbt

    Then you can either run it directly through sbt with the run command:
        run args file

    or you can run create a shell script by running:
        stage

    *Note: after running stage you can either create an alias with something like:
        path/to/main/project/directory/target/universal/stage/bin/pastiesbin

    or you can create a symbolic link and place it wherever you'd like.

Example Usage:
--------------
--------------

   Assuming you've created an alias:
        pastiesbin --lines 1 12 --expire 10M --name "A Simple Test" --privacy 0 --syntax scala source_file.scala

   In order to use this program you do need a valid pastebin developer key which can be easily obtained by creating an account with them.
   After obtaining a key, you need to edit the src/main/scala/Paste.scala file and replace the "Your developer key here" part with your developer
   key.  You should do this before you run the stage command.

   To get a complete listing of supported syntax highlighting options see: http://pastebin.com/api which is listed under the
   "Creating A New Paste, The 'api_paste_format' Paramater In Detail" section.

   A note on the pivacy setting:

        There are three options:
            0 -> public
            1 -> unlisted (The default)
            2 -> private (This does not work without a valid user key which is not supported at this time)

   A note on the expire setting:
        
        N   -> Never
        10M -> 10 Minutes
        1H  -> 1 Hour
        1D  -> 1 Day
        1W  -> 1 Week
        2W  -> 2 Weeks
        1M  -> 1 Month

Additional Notes:
----------------
----------------

    It is unlikely that I will add options beyond those already present due to this mainly being a project
    for my own use and the current options serve all my needs.
