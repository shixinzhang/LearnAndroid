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
	__vue_options__.__file = "/Users/zhangshixin/Documents/weex/weexdemo/image.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-6d6d17b6"
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
	  "wrapper": {
	    "alignItems": "center",
	    "marginTop": 120,
	    "width": 750
	  },
	  "panel": {
	    "width": 600,
	    "borderWidth": 3,
	    "borderStyle": "solid",
	    "borderColor": "#ff0000",
	    "padding": 15,
	    "marginBottom": 30,
	    "alignItems": "center"
	  },
	  "text": {
	    "fontSize": 36,
	    "lines": 3,
	    "color": "#666600",
	    "marginTop": 15
	  },
	  "logo": {
	    "width": 360,
	    "height": 82,
	    "backgroundColor": "#FF0000",
	    "width:active": 180,
	    "height:active": 82,
	    "backgroundColor:active": "#008000"
	  },
	  "image": {
	    "width": 750,
	    "height": 500,
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "borderColor": "#ff0000"
	  },
	  "desc": {
	    "position": "absolute",
	    "color": "#FFFFFF",
	    "marginLeft": 100,
	    "marginTop": 100,
	    "fontSize": 40
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

	exports.default = {
	  props: {
	    logoUrl: {
	      default: 'https://alibaba.github.io/weex/img/weex_logo_blue@3x.png'
	    },
	    target: {
	      default: 'World'
	    },
	    coverUrl: {
	      default: 'https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg'
	    }
	  },
	  methods: {
	    update: function update(e) {
	      this.target = 'Weex';
	    }
	  }
	};
	module.exports = exports['default'];

/***/ },
/* 3 */
/***/ function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('scroller', [_c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["panel"]
	  }, [_c('image', {
	    staticClass: ["logo"],
	    attrs: {
	      "src": _vm.logoUrl
	    }
	  }), _c('text', {
	    staticClass: ["text"]
	  }, [_vm._v("Weex 是一套简单易用的跨平台开发方案，能以 Web 的开发体验构建高性能、可扩展的原生应用。Vue 是一个轻量并且功能强大的渐进式前端框架")])]), _c('div', {
	    staticStyle: {
	      marginTop: "100px"
	    }
	  }, [_c('image', {
	    staticClass: ["image"],
	    attrs: {
	      "resize": "cover",
	      "src": _vm.coverUrl
	    }
	  }), _c('text', {
	    staticClass: ["desc"]
	  }, [_vm._v("cover")])]), _c('div', {
	    staticStyle: {
	      marginTop: "100px"
	    }
	  }, [_c('image', {
	    staticClass: ["image"],
	    attrs: {
	      "resize": "contain",
	      "src": _vm.coverUrl
	    }
	  }), _c('text', {
	    staticClass: ["desc"]
	  }, [_vm._v("contain")])])])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }
/******/ ]);