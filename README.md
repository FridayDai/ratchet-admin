Ratchet V2 Admin Portal
================

## Dependencies

1. Install **JDK1.8**

2. Install **gvm**

	```
    curl -s get.gvmtool.net | bash
    ```
3. Install **grails**

	```
	gvm install grails 2.4.4			
	```
	
	
## Compass

1. Install **Homebrew**

    ```
        ruby -e '$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)'
    ```
2. Install **Ruby**

  		 - Now that we have Homebrew installed, we can use it to install Ruby.
   		 We're going to use rbenv to install and manage our Ruby versions.
   		 To do this, run the following commands in your Terminal:

    ```
       brew install rbenv ruby-build

       # Add rbenv to bash so that it loads every time you open a terminal
       echo 'if which rbenv > /dev/null; then eval "$(rbenv init -)"; fi' >> ~/.bash_profile
       source ~/.bash_profile

       # Install Ruby 2.0.0 and set it as the default version
       rbenv install 2.0.0-p247
       rbenv global 2.0.0-p247

       # Check ruby version
       ruby -v
    ```
3. Install **Compass**

    ```
        gem update --system
        gem install compass
    ```

## Developing

	```
        grails run-app
        
        //If running on localhost should add NOT_SUPPORT_HTTPS ENV variable to true, as:
        grails run-app -DNOT_SUPPORT_HTTPS=true
    ```
    
    ```
        npm install
        bower install
        npm start
    ```
    
    
## Running 

1. Using **remote** backend server **http://54.164.12.147:8091/api/v1**

	```
  grails run-app
  //If running on localhost should add NOT_SUPPORT_HTTPS ENV variable to true, as:
  grails run-app -DNOT_SUPPORT_HTTPS=true
```

2. Using **locally** running backend server

	```
  vim grails-app/conf/resources/noredist/override.properties
  # change ratchetv2.server.url.host = http://54.164.12.147:8091/api to
  ratchetv2.server.url.host = http://localhost:8090/api
```

	- navigate to the backend server project

	```
  cd /where-your-projects-are/ratchet-v2-server/
```

    - (**Note**: if you haven't already, follow the 'Setup database' instructions in the README)

	```
  rabbitmq-server
```

   - Open a new console tab (same dir)
  
	```      
	./grailsw run-app -Dserver.port=8090
	```

  - Open a new console tab (same dir)
	
  ```	  
  cd /where-your-projects-are/ratchet-v2-admin-portal
  ./grailsw run-app
  //If running on localhost should add NOT_SUPPORT_HTTPS ENV variable to true, as:
  grails run-app -DNOT_SUPPORT_HTTPS=true
```
	- See http://localhost:8080


3. Run frontend process flow:

```
npm install
bower install
npm start
```

## Testing



## CDN

1. The variable CDN_ENABLE should be set as 'true' in env configuration.
2. Set one custom origin instance of clouldfront
2. The variable CDN_ASSET_DOMAIN_ADMIN should be set with clouldfront instance domain name


## Configuration Variables

- ELK_TCP_ADDR
- SERVER_URL
- CDN_ENABLE    # true | false | not define
- CDN_ASSET_DOMAIN_ADMIN    // just cloudfront url like: ```https://d1gdqclzwn7f9.cloudfront.net```
- NOT_SUPPORT_HTTPS    # true | false | not define

