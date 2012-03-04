# mailblaster
=============

Mass mailing in Clojure. Mailblaster lets you email a list of emails with single message. Each recipient is emailed their own message.


### Building

    $ lein uberjar

### Setup

You need to create a properties file with your smtp host information. The format for that is the following. It should be created in a file called `smtp.properties` in a directory called `.smtp` under your `$HOME` directory.

    host="SERVER"
    port=PORT
    user="USERNAME"
    pass="PASSWORD"

## Usage

Put your emails in a single file called `emails.csv`. Put each email on it's own line. Next write your email and save it in a file called `message.txt`.

Next come up with a subject for your message and remember your email address.

   $ java -jar mailblaster-1.0.0.jar -f your-email@foo.com -s "Your email subject" -e emacs.csv -m message.txt

There are other options which you can see with the followin.

    $ java -jar mailblaster-1.0.0.jar

## License

Distributed under the Eclipse Public License, the same as Clojure.
