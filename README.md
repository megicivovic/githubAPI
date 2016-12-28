# githubAPI

This is a Java console application that demonstrates the usage of Github API. 
It supports the following commands: 
* `ghtool` - displays the content of this help file
* `ghtool list -n X` - lists X latest repositories on github, where X parameter is optional - in that case
 default number of repositories is displayed
* `ghtool list {language} -n X` - lists X latest repositories on github written in a particular programming 
 language. The X parameter is also not required as in the previous command.
* `ghtool desc` - describes the usage of desc command
* `ghtool repo` - with this command you can create new repo on your github. For performing this command,
application needs to be registered in [Authorized applications] (https://github.com/settings/applications) with a client ID and client secret, 
 and also you will be asked to provide your github credentials (this is and additional feature expanding the requests made by SBG team)
