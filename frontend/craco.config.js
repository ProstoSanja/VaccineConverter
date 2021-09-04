const CracoLessPlugin = require('craco-less');
const path = require('path');

module.exports = {
    plugins: [
        {
            plugin: CracoLessPlugin,
            options: {
                lessLoaderOptions: {
                    lessOptions: {
                        modifyVars: {
                            '@primary-color': '#ffcc00',
                            '@link-color': '#003399',
                            'btn-primary-color': '#000',
                        },
                        javascriptEnabled: true,
                    },
                },
            },
        },
    ],
    webpack: {
        configure: (webpackConfig, { env, paths }) => {
            paths.appBuild = webpackConfig.output.path = path.resolve('./../src/main/resources/static/');
            return webpackConfig;
        }
    },
};
