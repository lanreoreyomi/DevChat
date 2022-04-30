# frontend

[//]: # ( Source: src/frontend/README.md
The frontend of the application is written in Vue Js. 

Vue is a JavaScript framework for building user interfaces. 
More information about Vue can be found at [Vue.js](https://vuejs.org/).


## Project setup
```
The front end requires a few dependencies to be installed.
1. Node.js
2. Vue.js

To install the dependencies, run the following command:

```
###Installing Node.js
```
Please refer to the official Node Js website for installation information. [https://nodejs.org/en/download/]

```
### Installing Vue.js
```
Please refer to the official Vue Js website for installation information. [Vue.js](https://vuejs.org/)

```
###Project Setup
```
Inside Vue.config.js, the following configuration is required to talk to the backend.

module.exports = {
  devServer:{
    port:3000,
    proxy:{
      '/api/v1': {
        target: 'http://localhost:5050',
        ws:true,
        changeOrigin: true
      }
    }

  }

}

You can send a request to the backend by using the following command:


   fetch('/api/v1/user')
      .then((response) => response.json())
      .then((data) => {
        console.log(data)
        this.users = data
      })
```

