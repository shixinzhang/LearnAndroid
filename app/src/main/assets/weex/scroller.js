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
	__vue_options__.__file = "/Users/zhangshixin/Documents/weex/weexdemo/scroller.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-48e1f240"
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
	  "scroll-horizontal": {
	    "flexDirection": "row"
	  },
	  "horizontal-desc": {
	    "position": "absolute",
	    "fontSize": 35,
	    "color": "#ee0000",
	    "textAlign": "center",
	    "width": 400,
	    "height": 250,
	    "marginTop": 50
	  },
	  "panel": {
	    "width": 600,
	    "height": 250,
	    "marginLeft": 75,
	    "marginBottom": 35,
	    "marginTop": 35,
	    "flexDirection": "column",
	    "justifyContent": "center",
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "borderColor": "rgb(162,217,193)",
	    "backgroundColor": "rgba(162,217,192,0.2)"
	  },
	  "text": {
	    "fontSize": 50,
	    "textAlign": "center",
	    "color": "#41B883"
	  },
	  "loading": {
	    "justifyContent": "center"
	  },
	  "scroller": {
	    "width": 700,
	    "height": 700,
	    "position": "relative",
	    "borderWidth": 3,
	    "borderStyle": "solid",
	    "borderColor": "rgb(162,217,192)",
	    "marginLeft": 25
	  },
	  "indicator": {
	    "color": "#888888",
	    "fontSize": 42,
	    "padding": 20,
	    "textAlign": "center"
	  },
	  "group": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "marginTop": 60,
	    "marginBottom": 30
	  },
	  "button": {
	    "width": 200,
	    "padding": 20,
	    "fontSize": 40,
	    "marginLeft": 30,
	    "marginRight": 30,
	    "textAlign": "center",
	    "color": "#41B883",
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "borderColor": "rgb(162,217,193)",
	    "backgroundColor": "rgba(162,217,192,0.2)"
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

	var modal = weex.requireModule('modal');
	var dom = weex.requireModule('dom');
	var LOAD_MORE_COUNT = 4;

	exports.default = {
		data: function data() {
			return {
				refreshing: false,
				showLoading: 'hide',
				lists: [1, 2, 3, 4, 5, 6, 7, 8],
				rows: []
			};
		},
		created: function created() {
			for (var i = 0; i < 30; i++) {
				this.rows.push('zsx' + i);
			}
		},

		methods: {
			onloading: function onloading(event) {
				var _this = this;

				modal.toast({ message: 'onloading', duration: 1 });
				this.showLoading = 'show';

				setTimeout(function () {
					var length = _this.lists.length;
					for (var i = length; i < length + LOAD_MORE_COUNT; ++i) {
						_this.lists.push(i + 1);
					}
					_this.showLoading = 'hide';
				}, 800);
			},
			onrefresh: function onrefresh(event) {
				var _this2 = this;

				console.log('is refreshing');
				modal.toast({ message: 'onrefresh', duration: 1 });
				this.refreshing = true;

				setTimeout(function () {
					_this2.refreshing = false;
				}, 800);
			},
			onpullingdown: function onpullingdown(event) {
				console.log('is onpullingdown');
				modal.toast({ message: 'pullingdown', duration: 1 });
			},
			onClick: function onClick(event) {
				modal.toast({ message: 'click', duration: 1 });
			},
			goto10: function goto10(count) {
				var e1 = this.$refs.item10[0];
				dom.scrollToElement(e1, {});
			},
			goto20: function goto20(count) {
				var e1 = this.$refs.item20[0];
				dom.scrollToElement(e1, { offset: 0 });
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
	  }, [_c('text', {
	    staticClass: ["horizontal-desc"]
	  }, [_vm._v("水平滚动条")]), _c('scroller', {
	    staticClass: ["scroll-horizontal"],
	    attrs: {
	      "scrollDirection": "horizontal"
	    }
	  }, _vm._l((_vm.lists), function(num) {
	    return _c('div', {
	      staticClass: ["cell"]
	    }, [_c('div', {
	      staticClass: ["panel"]
	    }, [_c('text', {
	      staticClass: ["text"],
	      on: {
	        "click": _vm.onClick
	      }
	    }, [_vm._v(_vm._s(num))])])])
	  })), _c('scroller', {
	    staticClass: ["scroller"]
	  }, [_c('refresh', {
	    staticClass: ["refresh"],
	    attrs: {
	      "display": _vm.refreshing ? 'show' : 'hide'
	    },
	    on: {
	      "refresh": _vm.onrefresh,
	      "pullingdown": _vm.onpullingdown
	    }
	  }, [_c('text', {
	    staticClass: ["indicator"]
	  }, [_vm._v("Refreshing...")])]), _vm._l((_vm.rows), function(name, index) {
	    return _c('div', {
	      ref: 'item' + index,
	      refInFor: true,
	      staticClass: ["panel"]
	    }, [_c('text', {
	      ref: 'text' + index,
	      refInFor: true,
	      staticClass: ["text"]
	    }, [_vm._v(_vm._s(name))])])
	  })], 2), _c('div', {
	    staticClass: ["group"]
	  }, [_c('text', {
	    staticClass: ["button"],
	    on: {
	      "click": _vm.goto10
	    }
	  }, [_vm._v("Go to 10")]), _c('text', {
	    staticClass: ["button"],
	    on: {
	      "click": _vm.goto20
	    }
	  }, [_vm._v("Go to 20")])])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }
/******/ ]);