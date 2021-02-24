var exec = require('cordova/exec');

var Adfurikun = {
    init: function () {
        exec(
            function () {
                console.log("init success ");
            },
            function () {
                console.log("init fail ");
            }, "Adfurikun", "init", []);
    },
    load: function () {
        exec(
            function () {
                console.log("load success ");
            },
            function () {
                console.log("load fail ");
            }, "Adfurikun", "load", []);
    },
    play: function () {
        exec(
            function () {
                console.log("play success ");
            },
            function () {
                console.log("play fail ");
            }, "Adfurikun", "play", []);
    },

}

module.exports = Adfurikun;