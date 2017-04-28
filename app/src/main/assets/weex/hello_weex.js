// { "framework": "Weex" }
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

	var __weex_template__ = __webpack_require__(1)
	var __weex_style__ = __webpack_require__(2)

	__weex_define__('@weex-component/088b6519e53591bd8d7c15d73a398cae', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/088b6519e53591bd8d7c15d73a398cae',undefined,undefined)

/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "scroller",
	  "style": {
	    "backgroundColor": "#3a3a3a"
	  },
	  "children": [
	    {
	      "type": "div",
	      "classList": [
	        "wrapper"
	      ],
	      "children": [
	        {
	          "type": "div",
	          "classList": [
	            "box",
	            "box1"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "text"
	              ],
	              "attr": {
	                "value": "zhang"
	              }
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "classList": [
	            "box",
	            "box2"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "text2"
	              ],
	              "attr": {
	                "value": "shi"
	              }
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "classList": [
	            "box",
	            "box3"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "text"
	              ],
	              "attr": {
	                "value": "xin"
	              }
	            }
	          ]
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 2 */
/***/ function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "position": "absolute",
	    "left": 0,
	    "top": 0,
	    "right": 0,
	    "bottom": 0,
	    "backgroundColor": "#cccccc"
	  },
	  "box": {
	    "margin": 50,
	    "padding": 20,
	    "width": 400,
	    "height": 400,
	    "position": "absolute",
	    "alignItems": "center",
	    "justifyContent": "center"
	  },
	  "box1": {
	    "left": 0,
	    "top": 0,
	    "backgroundColor": "#FF0000"
	  },
	  "box2": {
	    "left": 150,
	    "top": 150,
	    "backgroundColor": "#0055dd",
	    "borderRadius": 100
	  },
	  "box3": {
	    "left": 300,
	    "top": 300,
	    "backgroundImage": "linear-gradient(to bottom right, #a80077,#66ff00)",
	    "borderRadius": 200
	  },
	  "text": {
	    "color": "#adff2f",
	    "fontSize": 50,
	    "fontWeight": "bold",
	    "textDecoration": "underline"
	  },
	  "text2": {
	    "color": "#adff2f",
	    "fontSize": 50,
	    "fontWeight": "900"
	  }
	}

/***/ }
/******/ ]);