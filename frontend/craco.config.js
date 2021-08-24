const CracoLessPlugin = require('craco-less');

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
};
