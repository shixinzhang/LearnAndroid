// { "framework": "Vue" }
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(1)
	)

	/* script */
	__vue_exports__ = __webpack_require__(2)

	/* template */
	var __vue_template__ = __webpack_require__(3)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/zhangshixin/Documents/weex/weexdemo/web.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-adf693c4"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__
	module.exports.el = 'true'
	new Vue(module.exports)


/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = {
	  "group": {
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "marginTop": 20,
	    "marginLeft": 30,
	    "marginRight": 30
	  },
	  "input": {
	    "width": 700,
	    "fontSize": 36,
	    "padding": 15,
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "borderColor": "#BBBBBB"
	  },
	  "button": {
	    "width": 225,
	    "textAlign": "center",
	    "backgroundColor": "#D3D3D3",
	    "padding": 15,
	    "marginBottom": 30,
	    "fontSize": 30
	  },
	  "webview": {
	    "width": 700,
	    "height": 820,
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "borderColor": "#41B883",
	    "marginLeft": 30,
	    "marginRight": 30
	  }
	}

/***/ },
/* 2 */
/***/ function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var webview = weex.requireModule('webview');
	var modal = weex.requireModule('modal');

	exports.default = {
		data: function data() {
			return {
				url: 'http://m.yaomaiche.com'
			};
		},

		methods: {
			loadURL: function loadURL(event) {
				var _this = this;

				this.url = this.$refs.input.value;
				modal.toast({ message: 'load url:' + this.url });
				setTimeout(function () {
					console.log('webview go back');
					modal.toast({ message: 'webview will go back' });
					webview.goBack(_this.$refs.webview);
				}, 10000);
			},
			reload: function reload(event) {
				console.log('webview will reload');
				modal.toast({ message: 'reload' });
				webview.reload(this.$refs.webview);
			},
			start: function start(event) {
				console.log('pagestart', event);
				modal.toast({ message: 'pagestart' });
			},
			finish: function finish(event) {
				console.log('pagefinish', event);
				modal.toast({ message: 'pagefinish' });
			},
			error: function error(event) {
				console.log('error', event);
				modal.toast({ message: 'error' });
			}
		}
	};
	module.exports = exports['default'];

/***/ },
/* 3 */
/***/ function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["group"]
	  }, [_c('input', {
	    ref: "input",
	    staticClass: ["input"],
	    attrs: {
	      "type": "url",
	      "autofocus": "false",
	      "value": "https://m.yaomaiche.com"
	    }
	  })]), _c('div', {
	    staticClass: ["group"]
	  }, [_c('text', {
	    staticClass: ["button"],
	    on: {
	      "click": _vm.loadURL
	    }
	  }, [_vm._v("loadURL")]), _c('text', {
	    staticClass: ["button"],
	    on: {
	      "click": _vm.reload
	    }
	  }, [_vm._v("reload")])]), _c('web', {
	    ref: "webview",
	    staticClass: ["webview"],
	    attrs: {
	      "src": _vm.url
	    },
	    on: {
	      "pagestart": _vm.start,
	      "pagefinish": _vm.finish,
	      "error": _vm.error
	    }
	  })], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }
/******/ ]);