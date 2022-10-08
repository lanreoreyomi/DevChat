const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true
})

module.exports = {
    devServer:{
        port:3000,
        proxy:{
            '/api/v1': {
                target: 'http://localhost:5050/api/v1/',
                ws:true,
                changeOrigin: true,
                pathRewrite: {
                    '^/api/v1': ''
                }
            }
        }

    }

}


