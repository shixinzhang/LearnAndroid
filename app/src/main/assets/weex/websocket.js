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
	var __weex_script__ = __webpack_require__(3)

	__weex_define__('@weex-component/2df6ea99e0763afa7ffcc07fa0ce462c', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/2df6ea99e0763afa7ffcc07fa0ce462c',undefined,undefined)

/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "scroller",
	  "children": [
	    {
	      "type": "div",
	      "children": [
	        {
	          "type": "div",
	          "style": {
	            "backgroundColor": "#286090"
	          },
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "title"
	              ],
	              "style": {
	                "height": 80,
	                "padding": 20,
	                "color": "#FFFFFF"
	              },
	              "attr": {
	                "value": "websocket"
	              }
	            }
	          ]
	        },
	        {
	          "type": "input",
	          "attr": {
	            "type": "text",
	            "placeholder": "please input message to send",
	            "autofocus": "false",
	            "value": ""
	          },
	          "classList": [
	            "input"
	          ],
	          "events": {
	            "change": "onchange",
	            "input": "oninput"
	          },
	          "id": "input"
	        },
	        {
	          "type": "div",
	          "style": {
	            "flexDirection": "row",
	            "justifyContent": "center"
	          },
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "button"
	              ],
	              "events": {
	                "click": "connect"
	              },
	              "attr": {
	                "value": "connect"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "button"
	              ],
	              "events": {
	                "click": "send"
	              },
	              "attr": {
	                "value": "send"
	              }
	            },
	            {
	              "type": "text",
	              "classList": [
	                "button"
	              ],
	              "events": {
	                "click": "close"
	              },
	              "attr": {
	                "value": "close"
	              }
	            }
	          ]
	        },
	        {
	          "type": "div",
	          "classList": [
	            "panel"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": "method = send"
	              }
	            }
	          ]
	        },
	        {
	          "type": "text",
	          "style": {
	            "color": "#000000",
	            "height": 80
	          },
	          "attr": {
	            "value": function () {return this.sendinfo}
	          }
	        },
	        {
	          "type": "div",
	          "classList": [
	            "panel"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": "method = onopen"
	              }
	            }
	          ]
	        },
	        {
	          "type": "text",
	          "style": {
	            "color": "#000000",
	            "height": 80
	          },
	          "attr": {
	            "value": function () {return this.onopeninfo}
	          }
	        },
	        {
	          "type": "div",
	          "classList": [
	            "panel"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": "method = onmessage"
	              }
	            }
	          ]
	        },
	        {
	          "type": "text",
	          "style": {
	            "color": "#000000",
	            "height": 80
	          },
	          "attr": {
	            "value": function () {return this.onmessage}
	          }
	        },
	        {
	          "type": "div",
	          "classList": [
	            "panel"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": "method = onclose"
	              }
	            }
	          ]
	        },
	        {
	          "type": "text",
	          "style": {
	            "color": "#000000",
	            "height": 80
	          },
	          "attr": {
	            "value": function () {return this.oncloseinfo}
	          }
	        },
	        {
	          "type": "div",
	          "classList": [
	            "panel"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": "method = onerror"
	              }
	            }
	          ]
	        },
	        {
	          "type": "text",
	          "style": {
	            "color": "#000000",
	            "height": 80
	          },
	          "attr": {
	            "value": function () {return this.onerrorinfo}
	          }
	        },
	        {
	          "type": "div",
	          "classList": [
	            "panel"
	          ],
	          "children": [
	            {
	              "type": "text",
	              "classList": [
	                "title"
	              ],
	              "attr": {
	                "value": "method = close"
	              }
	            }
	          ]
	        },
	        {
	          "type": "text",
	          "style": {
	            "color": "#000000",
	            "height": 80
	          },
	          "attr": {
	            "value": function () {return this.closeinfo}
	          }
	        }
	      ]
	    }
	  ]
	}

/***/ },
/* 2 */
/***/ function(module, exports) {

	module.exports = {
	  "input": {
	    "fontSize": 40,
	    "height": 80,
	    "width": 600
	  },
	  "button": {
	    "fontSize": 36,
	    "width": 150,
	    "height": 100,
	    "color": "#41B883",
	    "textAlign": "center",
	    "paddingTop": 10,
	    "paddingBottom": 10,
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "marginRight": 20,
	    "borderColor": "rgb(162,217,192)",
	    "backgroundColor": "rgba(162,217,192,0.2)"
	  },
	  "panel": {
	    "backgroundColor": "#D3D3D3",
	    "marginTop": 20
	  },
	  "title": {
	    "height": 80,
	    "padding": 20,
	    "content": "#000000"
	  }
	}

/***/ },
/* 3 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var websocket = __weex_require__('@weex-module/webSocket');
	module.exports = {
		data: function () {return {
			connectinfo: '',
			sendinfo: '',
			onopeninfo: '',
			onmessage: '',
			oncloseinfo: '',
			onerrorinfo: '',
			closeinfo: '',
			txtInput: '',
			navBarHeight: 88,
			title: 'Navigator',
			dir: 'examples',
			baseURL: ''
		}},
		methods: {
			connect: function connect() {
				websocket.WebSocket('ws://echo.websocket.org', '');
				var self = this;
				self.onopeninfo = 'connecting...';
				websocket.onopen = function (e) {
					self.onopeninfo = 'websocket open';
				};
				websocket.onmessage = function (e) {
					self.onmessage = e.data;
				};
				websocket.onerror = function (e) {
					self.onerrorinfo = e.data;
				};
				websocket.onclose = function (e) {
					self.onopeninfo = 'closed';
					self.onerrorinfo = e.code;
				};
			},
			send: function send(e) {
				var input = this.$el('input');
				input.blur();
				websocket.send(this.txtInput);
				this.sendinfo = this.txtInput;
			},
			oninput: function oninput(e) {
				this.txtInput = event.value;
			},
			close: function close(e) {
				websocket.close();
			}
		}
	};}
	/* generated by weex-loader */


/***/ }
/******/ ]);