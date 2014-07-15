var serverUrl = "http://localhost:8080";
var ie = !-[1, ];
var ie6 = !-[1, ] && !window.XMLHttpRequest;
var imPanelRelativeTop = 0;

function log(msg) {
    /// <summary>
    /// 输入日志
    /// </summary>
    /// <param name="msg"></param>/
    if (DEBUG()) {
        if (document.getElementById("debugOutput"))
            document.getElementById("debugOutput").innerHTML += msg + "<br />";
    }
}

function DEBUG() {
    /// <summary>
    /// 是否为BEGUG模式
    /// </summary>
    /// <returns type=""></returns>
    return false;
}

function setCookie(key, value, options) {
    /// <summary>
    /// 设置Cookie
    /// </summary>
    /// <param name="key">键</param>
    /// <param name="value">值</param>
    /// <param name="options">cookie选项</param>
    /// <returns type=""></returns>
    if (options === undefined)
        options = { expires: '' };

    if (value === null)
        options.expires = -1;

    if (typeof options.expires === 'number') {
        var days = options.expires, t = options.expires = new Date();
        t.setDate(t.getDate() + days);
    }

    value = new String(value);

    return (document.cookie = [
		encodeURIComponent(key), '=', encodeURIComponent(value),
		options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
		options.path ? '; path=' + options.path : '',
		options.domain ? '; domain=' + options.domain : '',
		options.secure ? '; secure' : ''
    ].join(''));
}

function getCookie(key) {
    /// <summary>
    /// 获取cookie值
    /// </summary>
    /// <param name="key">键</param>
    /// <returns type="">值</returns>
    var cookies = document.cookie.split('; ');
    var result = key ? null : {};
    for (var i = 0, l = cookies.length; i < l; i++) {
        var parts = cookies[i].split('=');
        var name = parts.shift();
        var cookie = parts.join('=');

        if (key && key === name) {
            result = cookie;
            break;
        }

        if (!key) {
            result[name] = cookie;
        }
    }
    return result;
}

//用户Session
var userSession = {
    userList: [], //最近联系人列表
    token: null, //token
    id: null,  //用户Id
    name: null, //用户名称
    isActivate: false, //是否活动
    _timer: null,  //消息获取定时器
    _timerStatus: null, //消息状态定时器
    init: function () {
        /// <summary>
        /// 初始化 token,获取最近联系人，并填充最近联系人列表
        /// </summary>
        connectProxy.connect(null, function () {
            connectProxy.getNearlyLinkman(function (data) {
                userSession.showUserList();
            });
        });
        this.start();
    },
    addUserToList: function (id, name, status, msg, thumb) {
        /// <summary>
        /// 添加联系人到联系人列表
        /// </summary>
        /// <param name="id">Id</param>
        /// <param name="name">名称</param>
        /// <param name="status">状态</param>
        /// <param name="msg">消息</param>
        /// <param name="thumb">头像</param>
        if (this.userList[id] === undefined)
            this.userList[id] = { 'id': id, 'name': name, 'status': status, 'msg': msg, 'thumb': thumb };
        else
            this.userList[id].msg = msg;
    },
    showUserList: function () {
        /// <summary>
        /// 填充最近联系人列表   有消息的显示在前面
        /// </summary>
        var data = userSession.userList;
        var userListHtml = '';
        var flag = false;
        var msgcount = 0;

        for (var id in data) {
            var online = 'offline';
            var status = 'status';
            if (data[id].status == 1)
                online = 'online';

            if (data[id].msg && data[id].msg > 0) {
                status = 'news';
                msgcount += data[id].msg;
                flag = true;
                userListHtml += formatImContact(online, id, data[id].name, data[id].thumb, status);
            }
        }

        for (id in data) {
            online = 'offline';
            status = 'status';
            if (data[id].status == 1)
                online = 'online';

            if (!(data[id].msg && data[id].msg > 0)) {
                userListHtml += formatImContact(online, id, data[id].name, data[id].thumb, status);
            }
        }

        if (flag)
            onLamp(msgcount);
        else
            offLamp();
        document.getElementById('_im_contact').innerHTML = userListHtml;
    },
    start: function () {
        /// <summary>
        /// 判断Session是否是活动状态  开始循环获取消息和消息状态
        /// </summary>
        if (this.isActivate)
            return;
        this.isActivate = true;
        this.activate();
    },
    activate: function () {
        /// <summary>
        /// 设置消息获取定时和消息状态定时器
        /// </summary>
        this._timer = setTimeout("userSession.pullMessage()", 0);
        userSession.pullMessageStatus();
        this._timerStatus = setInterval("userSession.pullMessageStatus()", 10000);
    },
    pullMessageStatus: function () {
        /// <summary>
        /// 获取消息状态
        /// </summary>
        connectProxy.getMessageStatus(function (data) {
            if (data == null || data.statuses == null)
                return;
            for (var i = 0; i < data.statuses.length; i++) {
                var uid = userList.currentIndex;

                if (uid != data.statuses[i].fromId)
                    userSession.addUserToList(data.statuses[i].fromId, data.statuses[i].fromName, data.statuses[i].status, data.statuses[i].count, data.statuses[i].thumb);
                userSession.showUserList();
            }
        });
    },
    pullMessage: function () {
        /// <summary>
        /// 获取消息  
        /// </summary>
        if (userList.current() != null)
            connectProxy.recv(userList.current().toId, function (data) {
                if (data == null)
                    return;
                if (data.success) {
                    for (var i = 0; i < data.messages.length; i++) {
                        userList.current().recvMessage(data.messages[i]);
                    }
                }
            });

        if (this.isActivate)
            this._timer = setTimeout("userSession.pullMessage()", 3000);
    },
    stop: function () {
        /// <summary>
        /// 停止session  清除定时器等
        /// </summary>
        if (!this.isActivate)
            return;
        clearTimeout(this._timer);
        userList.currentIndex = "";
        this.isActivate = false;
        this.deactivate();
    },
    deactivate: function () {
    },
    close: function () {
        /// <summary>
        /// 关闭session
        /// </summary>
        clearInterval(this._timerStatus);
        userSession.token = null;
        userSession.id = null;
        userSession.userList = [];
        userSession.name = null;
        userSession.isActivate = false;
    }
};

if (typeof window.userSession == 'undefined')
    window.userSession = userSession;

//联系人列表
var userList = {
    list: [], //列表
    currentIndex: '', //当前对话联系人
    current: function () {
        /// <summary>
        /// 获取当前联系人信息
        /// </summary>
        /// <returns type=""></returns>
        return this.getUser(this.currentIndex);
    },
    select: function (toId) {
        /// <summary>
        /// 打开对话窗口，并开始执行循环
        /// </summary>
        /// <param name="toId"></param>
        this.currentIndex = toId;
        if (userSession.userList[toId] != undefined)
            delete userSession.userList[toId].msg;
        userSession.showUserList();
        if (this.getUser(this.currentIndex) != null) {
            userSession.start();
            im_select();
            this.getUser(this.currentIndex).renderHtml("_im_chat_list");
        }
    },
    addUser: function (user) {
        /// <summary>
        /// 添加联系人到列表，并打开对话窗口
        /// </summary>
        /// <param name="user"></param>
        this.list[user.toId] = user;
        this.select(user.toId);
        if (DEBUG())
            log(user.toId + " aready added!");
    },
    removeUser: function (user) {
        /// <summary>
        /// 移除联系人
        /// </summary>
        /// <param name="user">联系人信息</param>
        this.list[user.toId] = undefined;
        if (DEBUG())
            log(user.toId + " aready removed!");
    },
    getUser: function (toId) {
        /// <summary>
        /// 获取联系人信息
        /// </summary>
        /// <param name="toId">联系人id</param>
        /// <returns type="">联系人信息</returns>
        if (toId == '' || toId === undefined)
            return null;
        if (typeof this.list[toId] == 'undefined' || this.list[toId] == null)
            if (typeof userSession.userList[toId] != 'undefined' && userSession.userList[toId] != null)
                this.createUser(toId, userSession.userList[toId].name);
            else
                return null;

        return this.list[toId];
    },
    getUserList: function () {
        /// <summary>
        /// 获取全部联系人信息
        /// </summary>
        /// <returns type="">联系人信息集合</returns>
        return this.list;
    },
    createUser: function (toId, to) {
        /// <summary>
        /// 创建联系人
        /// </summary>
        /// <param name="toId"></param>
        /// <param name="to"></param>
        /// <returns type=""></returns>
        return new User(toId, to);
    }
};

if (typeof window.userList == 'undefined')
    window.userList = userList;

function User(toId, to) {
    /// <summary>
    /// 联系人信息，初始化，打开对话框
    /// </summary>
    /// <param name="toId"></param>
    /// <param name="to"></param>
    if (DEBUG())
        log(to + ": " + toId + " the user class begin construrctor!");
    this.to = to;
    this.toId = toId;
    this.tokenInit = false;
    this.token = null;

    connectProxy.connect(toId, function (data, thisObj) {
        if (data.success) {
            thisObj.token = data.token;
            thisObj.toInfo = data.to;
            thisObj.tokenInit = true;
            userList.addUser(thisObj);
            //im_select();
        }
    }, this);
    if (DEBUG())
        log(to + ": " + toId + " the User class aready constructor compeleted!");
}

User.prototype = {
    contentHtml: '',
    toId: null,
    to: null,
    toInfo: {},
    token: null,
    reConnect: function () {
        /// <summary>
        /// 重新连接
        /// </summary>
        connectProxy.connect(this.toId, function (data, thisObj) {
            if (data.success) {
                thisObj.token = data.token;
                thisObj.toInfo = data.to;
                thisObj.tokenInit = true;
            }
        }, this);
    },
    recvMessage: function (message) {
        /// <summary>
        /// 处理接收到的消息
        /// </summary>
        /// <param name="message">消息</param>
        var content = message.content;
        var totype = 'chat-others';

        if (message.fromId == userSession.id)
            totype = 'chat-self';

        if (message.fromId == "system")
            totype = "chat-system";

        this.contentHtml += formatImMessage(totype, message.from, message.sendTime, content);
        this.renderHtml("_im_chat_list");
    },
    sendMessage: function (message) {
        /// <summary>
        /// 发送消息
        /// </summary>
        /// <param name="message">消息</param>
        var thisObj = this;
        var recvMessage = this.recvMessage;
        connectProxy.send(this.toId,
						  message,
						  function (data) {
						      if (data.success) {
						          for (var i = 0; i < data.messages.length; i++)
						              recvMessage.call(thisObj, data.messages[i]);
						      } else {
						          if (data.errorCode == 105) {
						              var systemErrorMessage = { content: "请等待对方给您回复!", fromId: "system", from: "系统消息", sendTime: data.time };
						              recvMessage.call(thisObj, systemErrorMessage);
						          }
						      }
						  });
    },
    renderHtml: function (id) {
        /// <summary>
        /// 显示消息
        /// </summary>
        /// <param name="id">显示消息的html Element id</param>
        document.getElementById(id).innerHTML = this.contentHtml;
        document.getElementById(id).scrollTop = document.getElementById(id).scrollHeight;
    },
    destory: function () {
        /// <summary>
        /// 析构函数
        /// </summary>
        if (DEBUG())
            log(this.toId + " the User begin destory!");
        userList.removeUser(this);
        this.contentHtml = null;
        this.toId = null;
        this.to = null;
        this.token = null;
        if (DEBUG())
            log(this.toId + " the User aready destory completed!");
    }
};

function ajaxInit() {
    /// <summary>
    /// ajax 初始化
    /// </summary>
    /// <returns type=""></returns>
    var hidenFrame = document.createElement("iframe");
    var name = "__Ajax" + new Date().getTime();

    hidenFrame.name = name;
    hidenFrame.id = name;
    hidenFrame.style.display = "none";
    document.body.appendChild(hidenFrame);
    window.frames[name].name = name;
    return hidenFrame;
}

function ajaxRequest(settings) {
    var callbackFuncName = "__ajaxCallback" + new Date().getTime();
    window[callbackFuncName] = function (data) {
        if (typeof settings.success == 'function')
            settings.success(data);
        delete window[callbackFuncName];
        window[callbackFuncName] = null;
    };

    var hidenFrame = ajaxInit();

    var ajaxLoadEvent = function () {
        try {
            eval(hidenFrame.contentWindow.document.body.innerHTML);
        } catch (e) {
            ;
        }
        document.body.removeChild(hidenFrame);
    };

    if (hidenFrame.attachEvent)
        hidenFrame.attachEvent("onload", ajaxLoadEvent);
    else if (hidenFrame.addEventListener)
        hidenFrame.addEventListener("load", ajaxLoadEvent);
    else if (hidenFrame.onloadeddata)
        hidenFrame.onloadeddata = ajaxLoadEvent;
    else
        hidenFrame.onload = ajaxLoadEvent;

    var ajaxForm = document.createElement("form");
    ajaxForm.target = hidenFrame.name;
    ajaxForm.method = settings.type;

    var callback = document.createElement("input");
    callback.type = "hidden";
    callback.name = "callback";
    callback.value = callbackFuncName;
    ajaxForm.appendChild(callback);

    if (typeof settings.url != 'undefined')
        ajaxForm.action = settings.url;

    for (var setting in settings) {
        if (setting == 'data') {
            var data = settings[setting];
            var input = null;

            for (var key in data) {
                input = document.createElement("input");
                input.type = "hidden";
                input.name = key;
                input.value = data[key];
                ajaxForm.appendChild(input);
            }
        }
    }

    document.body.appendChild(ajaxForm);
    ajaxForm.submit();
    document.body.removeChild(ajaxForm);
}

function ajaxCrossGet(settings) {
    /// <summary>
    /// ajax跨域请求
    /// </summary>
    /// <param name="settings">参数</param>
    var ajaxForm = document.createElement("script");
    var callbackFuncName = "__ajaxCallback" + new Date().getTime();
    window[callbackFuncName] = function (data) {
        if (typeof settings.success == 'function')
            settings.success(data);
        if (!ie) {
            delete window[callbackFuncName];
        }
        if (!ie6) {
            document.body.removeChild(ajaxForm);
        }
    };

    var input = settings.url + "?";

    if (typeof settings.success == 'function')
        input += "callback=" + callbackFuncName + "&";

    for (var setting in settings) {
        if (setting == 'data') {
            var data = settings[setting];

            for (var key in data)
                input += key + "=" + data[key] + "&";
        }
    }

    input += "_t=" + new Date().getTime();
    ajaxForm.src = input;
    ajaxForm.type = "text/javascript";
    document.body.appendChild(ajaxForm);
}

//连接代理
var connectProxy = {
    url: serverUrl, //服务器地址
    get: function (url, data, async, callback) {
        /// <summary>
        /// Get请求
        /// </summary>
        /// <param name="url">url</param>
        /// <param name="data">参数</param>
        /// <param name="async"></param>
        /// <param name="callback">回调函数</param>
        ajaxCrossGet({
            type: 'GET',
            url: url,
            data: data,
            success: callback
        });
    },
    post: function (url, data, async, callback) {
        /// <summary>
        /// Post请求
        /// </summary>
        /// <param name="url">url</param>
        /// <param name="data">参数</param>
        /// <param name="async"></param>
        /// <param name="callback">回调函数</param>
        ajaxRequest({
            type: 'POST',
            url: url,
            data: data,
            success: callback
        });
    },
    setUrl: function (url) {
        /// <summary>
        /// 设置服务器地址
        /// </summary>
        /// <param name="url">服务器url</param>
        if (typeof url != 'undefined')
            this.url = url;
    },
    connect: function (toId, completed, thisObj) {
        /// <summary>
        /// 连接服务器
        /// </summary>
        /// <param name="toId">对方Id</param>
        /// <param name="completed">完成回调</param>
        /// <param name="thisObj">回调参数</param>
        if (userSession.id == null)
            userSession.id = getCookie("tempId");

        var data = {};

        if (userSession.id != null)
            data.fromId = userSession.id;

        if (toId != null)
            data.toId = toId;

        this.get(this.url + "/api/token",
				 data,
				 false,
				 function (data) {
				     if (data.success) {
				         if (toId == null)
				             userSession.token = data.token;

				         userSession.id = data.from.id;
				         userSession.thumb = data.from.thumb;
				         userSession.name = data.from.nickName;

				         if (data.hasCreateTempId) {
				             setCookie('tempId', data.tempId, { expires: 365 });
				             setCookie('tempName', data.tempName, { expires: 365 });
				         }
				     } else {
				         ;
				     }

				     if (typeof completed == 'function') {
				         completed(data, thisObj);
				     }
				 });
    },
    recv: function (toId, completed) {
        /// <summary>
        /// 接收消息，如果token过期，则重新获取
        /// </summary>
        /// <param name="toId">对话对方Id</param>
        /// <param name="completed">完成回调</param>
        /// <returns type="">是否成功</returns>
        if (typeof this.url == 'undefined' || this.url == null)
            return false;

        if (userSession.token == null || userSession.id == null)
            return false;

        if (toId != null && (userList.getUser(toId) == null || !userList.getUser(toId).tokenInit))
            return false;

        var data = {};
        data.token = userList.getUser(toId).token;
        data.fromId = userSession.id;

        if (toId != null)
            data.toId = toId;

        this.get(this.url + "/api/message",
				 data,
				 false,
				 function (data) {
				     if (typeof completed == 'function')
				         completed(data);
				     if (!data.success) {
				         if (data.errorCode == 103)
				             userList.getUser(toId).reConnect();
				     }
				 });

        return true;
    },
    send: function (toId, content, completed) {
        /// <summary>
        /// 发送消息，如果token过期，则重新获取token并重新发送
        /// </summary>
        /// <param name="toId">对话对方Id</param>
        /// <param name="content">消息内容</param>
        /// <param name="completed">回调参数</param>
        /// <returns type="">是否成功</returns>
        if (typeof this.url == 'undefined' || this.url == null)
            return false;

        if (userSession.id == null || toId == null)
            return false;

        if (userSession.token == null && toId == userSession.id)
            return false;

        if (toId != userSession.id && (userList.getUser(toId) == null || !userList.getUser(toId).tokenInit))
            return false;

        this.get(this.url + "/api/message",
				  {
				      token: userList.getUser(toId).token,
				      fromId: userSession.id,
				      toId: toId,
				      content: content
				  },
				  false,
				  function (data) {
				      if (typeof completed == 'function') {
				          completed(data);
				      }
				      if (!data.success) {
				          if (data.errorCode == 103) {
				              connectProxy.connect(toId, function () {
				                  connectProxy.send(toId, content, compeleted);
				              });
				          }
				      }
				  });

        return true;
    },
    getMessageStatus: function (completed) {
        /// <summary>
        /// 获取消息状态  token过期，则重新获取token
        /// </summary>
        /// <param name="completed">回调</param>
        /// <returns type="">是否成功</returns>
        if (typeof this.url == 'undefined' || this.url == null)
            return false;

        if (userSession.token == null || userSession.id == null)
            return false;

        this.get(this.url + "/api/messageStatus",
				  {
				      token: userSession.token,
				      fromId: userSession.id
				  },
				  false,
				  function (data) {
				      if (data.success) {
				      } else {
				          if (data.errorCode == 103) {
				              connectProxy.connect();
				          }
				      }

				      if (typeof completed == 'function')
				          completed(data);
				  }
				 );

        return true;
    },
    getNearlyLinkman: function (completed) {
        /// <summary>
        /// 获取最近联系人，如果token过期，则重新获取token
        /// </summary>
        /// <param name="completed">回调函数</param>
        /// <returns type="">是否成功</returns>
        if (typeof this.url == 'undefined' || this.url == null)
            return false;

        if (userSession.token == null || userSession.id == null)
            return false;

        this.get(this.url + "/api/nearlyLinkman",
				 {
				     token: userSession.token,
				     fromId: userSession.id
				 },
				 false,
				 function (data) {
				     if (data.success) {
				         for (var i = 0; i < data.linkmans.length; i++)
				             userSession.userList[data.linkmans[i].id] = data.linkmans[i];
				         if (typeof completed == 'function')
				             completed(data);
				     } else {
				         if (data.errorCode == 103) {
				             connectProxy.connect();
				             setTimeout(function () { connectProxy.getNearlyLinkman(completed); }, 10000);
				         }
				     }
				 });

        return true;
    }
};


function addStyle(url) {
    /// <summary>
    /// 添加css
    /// </summary>
    /// <param name="url">css url</param>
    var head = document.documentElement.getElementsByTagName("head")[0];
    var csslink = document.createElement("link");
    head.appendChild(csslink);
    csslink.rel = "stylesheet";
    csslink.type = "text/css";
    csslink.href = url;
}

function addHtmlElement(elem) {
    /// <summary>
    /// 添加html标签到body
    /// </summary>
    /// <param name="elem"></param>
    var body = document.body;
    body.appendChild(elem);
}

function createImToolbar() {
    /// <summary>
    /// 创建悬浮框
    /// </summary>
    if (document.getElementById("_im_im-toolbar") != null) return;
    var toolBar = document.createElement("div");
    addHtmlElement(toolBar);
    toolBar.id = "_im_im-toolbar";
    toolBar.className = "im-toolbar im-fold";
    toolBar.innerHTML = '<div id="_im_tb1" class="toolbar-hd"> \
							 <span id="_im_status" class="status" onclick="showImToolbar()"></span> \
							 <span id="_im_title" class="title">最近联系人</span> \
							 <div id="_im_operate" class="operate"><span class="shrink" onclick="hideImToolbar()"></span></div> \
						 </div> \
						 <div id="_im_tb2" class="toolbar-bd"> \
							 <div class="recent-contact"> \
								 <ul id="_im_contact"> \
								 <li>联系人列表加载失败...</li> \
								 </ul> \
							 </div> \
						 </div>';
}

function formatImContact(online, wno, name, thumb, status) {
    /// <summary>
    /// 最近联系人html
    /// </summary>
    /// <param name="online">在线状态css(online,offline)</param>
    /// <param name="wno">id</param>
    /// <param name="name">name</param>
    /// <param name="thumb">头像</param>
    /// <param name="status">消息状态css(status,news)</param>
    /// <returns type=""></returns>
    return '<li class="' + online + '" onclick="userList.select(' + wno + ')"><span class="photo"><img style="width:30px; height:30px" src="' + thumb + '" alt="" /></span><span class="name">' + name + '</span><span class="' + status + '"></span></li>';
}

function createImPanel() {
    /// <summary>
    /// 创建对话div
    /// </summary>
    if (document.getElementById("_im_imPanel") != null) return;

    var imPanel = document.createElement("div");
    addHtmlElement(imPanel);
    imPanel.id = "_im_imPanel";
    imPanel.className = "im-chat";
    imPanel.style.width = "500px";
    imPanel.style.height = "385px";

    if (ie6) {
        imPanel.style.position = "absolute";
    } else {
        imPanel.style.position = "fixed";
    }
    imPanel.style.left = (document.body.clientWidth - parseInt(imPanel.style.width)) / 2 + "px";
    imPanel.style.top = (window.screen.availHeight - parseInt(imPanel.style.height)) / 2 + "px";
    imPanel.style.display = "none";
}

function formatUserInfo(info) {
    /// <summary>
    /// 填充对话div
    /// </summary>
    /// <param name="info">用户信息</param>
    /// <returns type="">html</returns>
    if (info.nickName === undefined)
        info.nickName = "加载中...";
    if (!info.thumb || info.thumb == undefined)
        info.thumb = '';

    var shopNameHtml = "";
    var linkmanHtml = "";
    var titleHtml = info.nickName + "(" + info.id + ")";
    if (!info.temp) {
        if (info.isMerchant) {
            titleHtml = info.shopName + "(" + info.id + ")";
            shopNameHtml = info.shopName;
            linkmanHtml = info.contractor;
        } else {
            linkmanHtml = info.contractor;
        }
    }

    return '<div class="chat-hd" id="chat-hd" onmousedown="window_onmousedown(event)" onmouseup="window_onmouseup(event)" onmousemove="window_onmousemove(event)"> \
				 <div class="photo"><img src="' + info.thumb + '" alt="" /></div> \
				 <span class="name">' + titleHtml + '</span> \
				 <div class="operate"><span class="close" onclick="closeImPanel()"></span></div> \
				 </div> \
					<div class="clearfix chat-bd"> \
					<div class="chat-window"> \
						<div class="chat-window-inner"> \
							<div id="_im_not_online_info" class="' + (info.status == "1" ? "chat-sys-msg-hidden" : "chat-sys-msg") + '">\
								<span>对方不在线，可能无法立即回复</span> \
								<a href="javascript:void(0)" onclick="closeOnLineInfo()" class="close" title="关闭"></a> \
							</div> \
							<div id="_im_chat_list" class="chat-list" style="height: '+ (info.status == "1" ? "230" : "203") + 'px;"> \
							</div> \
							<div class="chat-write"> \
                <div class="sendbox_bar"> \
                  <div class="sendbox_ac"> \
                    <span> \
                      <a class="sendicon send_face" title="发送表情" href="javascript:switchFace();">表情</a> \
                    </span> \
                  </div> \
                  <div class="sendbox_show"> \
                    <span class="sendbox_oth"> \
                      <a class="sendbox_his" href="#" target="_blank" title="查看私信记录">聊天记录</a> \
                    </span> \
                  </div> \
                </div> \
                <fieldset style="padding:0px;border-width:0px;margin:0px;"> \
                <div class="chat-textarea"><textarea id="_im_textbox" onkeydown="return keyDownProcess(event)"></textarea></div></fieldset> \
							<div class="chat-submit"><button  id="_im_btnsend" onclick="im_send(this)"></button></div> \
						</div> \
					</div> \
				</div> \
				<div class="chat-desc"> \
					<div class="logo"><img src="' + info.thumb + '" alt="" /></div> \
					<ul> \
						<li>' + shopNameHtml + '</li> \
						<li>联系人：' + linkmanHtml + '</li> \
						<li>联系电话：' + (info.mobile == null ? '' : info.mobile) + '</li> \
						<li>电子邮箱：' + (info.email == null ? '' : info.email) + '</li> \
						<li>' + (info.address == null ? '' : info.address) + '</li> \
					</ul> \
				</div> \
			' + 
			'<div id="__w_layer" style="left: 0px; display: none; bottom: 135px;" class="w_layer"> \
              <div class="bg"> \
  	                <div class="w_faces"> \
   	                    <ul class="faces_list clearfix"> \
                          <li><a onclick="return selectFace(this.title);" title="[草泥马]"><img src=\"' + serverUrl + '/client/css/faces/shenshou_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[神马]"><img src=\"' + serverUrl + '/client/css/faces/horse2_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[浮云]"><img src=\"' + serverUrl + '/client/css/faces/fuyun_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[给力]"><img src=\"' + serverUrl + '/client/css/faces/geili_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[围观]"><img src=\"' + serverUrl + '/client/css/faces/wg_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[威武]"><img src=\"' + serverUrl + '/client/css/faces/vw_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[熊猫]"><img src=\"' + serverUrl + '/client/css/faces/panda_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[兔子]"><img src=\"' + serverUrl + '/client/css/faces/rabbit_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[奥特曼]"><img src=\"' + serverUrl + '/client/css/faces/otm_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[囧]"><img src=\"' + serverUrl + '/client/css/faces/j_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[互粉]"><img src=\"' + serverUrl + '/client/css/faces/hufen_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[礼物]"><img src=\"' + serverUrl + '/client/css/faces/liwu_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[呵呵]"><img src=\"' + serverUrl + '/client/css/faces/smilea_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[嘻嘻]"><img src=\"' + serverUrl + '/client/css/faces/tootha_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[哈哈]"><img src=\"' + serverUrl + '/client/css/faces/laugh.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[可爱]"><img src=\"' + serverUrl + '/client/css/faces/tza_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[可怜]"><img src=\"' + serverUrl + '/client/css/faces/kl_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[挖鼻屎]"><img src=\"' + serverUrl + '/client/css/faces/kbsa_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[吃惊]"><img src=\"' + serverUrl + '/client/css/faces/cj_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[害羞]"><img src=\"' + serverUrl + '/client/css/faces/shamea_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[挤眼]"><img src=\"' + serverUrl + '/client/css/faces/zy_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[闭嘴]"><img src=\"' + serverUrl + '/client/css/faces/bz_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[鄙视]"><img src=\"' + serverUrl + '/client/css/faces/bs2_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[爱你]"><img src=\"' + serverUrl + '/client/css/faces/lovea_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[泪]"><img src=\"' + serverUrl + '/client/css/faces/sada_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[偷笑]"><img src=\"' + serverUrl + '/client/css/faces/heia_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[亲亲]"><img src=\"' + serverUrl + '/client/css/faces/qq_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[生病]"><img src=\"' + serverUrl + '/client/css/faces/sb_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[太开心]"><img src=\"' + serverUrl + '/client/css/faces/mb_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[懒得理你]"><img src=\"' + serverUrl + '/client/css/faces/ldln_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[右哼哼]"><img src=\"' + serverUrl + '/client/css/faces/yhh_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[左哼哼]"><img src=\"' + serverUrl + '/client/css/faces/zhh_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[嘘]"><img src=\"' + serverUrl + '/client/css/faces/x_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[衰]"><img src=\"' + serverUrl + '/client/css/faces/cry.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[委屈]"><img src=\"' + serverUrl + '/client/css/faces/wq_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[吐]"><img src=\"' + serverUrl + '/client/css/faces/t_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[打哈欠]"><img src=\"' + serverUrl + '/client/css/faces/k_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[抱抱]"><img src=\"' + serverUrl + '/client/css/faces/bba_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[怒]"><img src=\"' + serverUrl + '/client/css/faces/angrya_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[疑问]"><img src=\"' + serverUrl + '/client/css/faces/yw_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[馋嘴]"><img src=\"' + serverUrl + '/client/css/faces/cza_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[拜拜]"><img src=\"' + serverUrl + '/client/css/faces/88_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[思考]"><img src=\"' + serverUrl + '/client/css/faces/sk_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[汗]"><img src=\"' + serverUrl + '/client/css/faces/sweata_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[困]"><img src=\"' + serverUrl + '/client/css/faces/sleepya_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[睡觉]"><img src=\"' + serverUrl + '/client/css/faces/sleepa_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[钱]"><img src=\"' + serverUrl + '/client/css/faces/money_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[失望]"><img src=\"' + serverUrl + '/client/css/faces/sw_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[酷]"><img src=\"' + serverUrl + '/client/css/faces/cool_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[花心]"><img src=\"' + serverUrl + '/client/css/faces/hsa_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[哼]"><img src=\"' + serverUrl + '/client/css/faces/hatea_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[鼓掌]"><img src=\"' + serverUrl + '/client/css/faces/gza_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[晕]"><img src=\"' + serverUrl + '/client/css/faces/dizzya_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[悲伤]"><img src=\"' + serverUrl + '/client/css/faces/bs_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[抓狂]"><img src=\"' + serverUrl + '/client/css/faces/crazya_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[黑线]"><img src=\"' + serverUrl + '/client/css/faces/h_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[阴险]"><img src=\"' + serverUrl + '/client/css/faces/yx_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[怒骂]"><img src=\"' + serverUrl + '/client/css/faces/nm_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[心]"><img src=\"' + serverUrl + '/client/css/faces/hearta_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[伤心]"><img src=\"' + serverUrl + '/client/css/faces/unheart.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[猪头]"><img src=\"' + serverUrl + '/client/css/faces/pig.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[ok]"><img src=\"' + serverUrl + '/client/css/faces/ok_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[耶]"><img src=\"' + serverUrl + '/client/css/faces/ye_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[good]"><img src=\"' + serverUrl + '/client/css/faces/good_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[不要]"><img src=\"' + serverUrl + '/client/css/faces/no_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[赞]"><img src=\"' + serverUrl + '/client/css/faces/z2_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[来]"><img src=\"' + serverUrl + '/client/css/faces/come_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[弱]"><img src=\"' + serverUrl + '/client/css/faces/sad_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[蜡烛]"><img src=\"' + serverUrl + '/client/css/faces/lazu_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[钟]"><img src=\"' + serverUrl + '/client/css/faces/clock_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[话筒]"><img src=\"' + serverUrl + '/client/css/faces/m_org.gif"></a></li> \
                          <li><a onclick="return selectFace(this.title);" title="[蛋糕]"><img src=\"' + serverUrl + '/client/css/faces/cake.gif"></a></li> \
                       </ul> \
                   </div> \
               </div> \
            <div class="arrow_hui arrow_b"></div> \
        </div> \
    </div>';
}

function insertFace(text){
    document.getElementById("_im_textbox").focus();  
    if(document.all){ // for IE
        range = document.selection.createRange();
        document.selection.empty();  
        range.text = text;  
        range.collapse();  
        range.select();
    }  
    else{
        var a = document.getElementById("_im_textbox");
        var start = a.selectionStart;
        a.value = a.value.substr(0, a.selectionStart) + text + a.value.substr(a.selectionStart);
        a.selectionStart = start + text.length;
        a.selectionEnd = start + text.length;
    } 
}

function selectFace(face) {
    insertFace(face);
    hideFace();
    return false;
}

function switchFace() {
    var faceDiv = document.getElementById("__w_layer");
    
    if (faceDiv.style.display == "" || faceDiv.style.display == "none")
        showFace();
    else
        hideFace();
}

function showFace() {
    var faceDiv = document.getElementById("__w_layer");
    faceDiv.style.display = "block";
}

function hideFace() {
    var faceDiv = document.getElementById("__w_layer");
    faceDiv.style.display = "none";
}

function closeOnLineInfo() {
    document.getElementById("_im_not_online_info").style.display = "none";
    document.getElementById("_im_chat_list").style.height = "230px";
    return false;
}

function im_send(btn) {
    /// <summary>
    /// 发送消息按钮事件处理方法
    /// </summary>
    /// <param name="btn">发送消息按钮</param>
    /// <returns type=""></returns>
    var txtbox = document.getElementById("_im_textbox");
    if (txtbox.value == "")
        return "";

    var content = txtbox.value.replace(/[\r\n]/g, "<br />");
    if (content.length > 250) {
        alert("内容超过最大长度");
        return false;
    }

    btn.disabled = true;
    userList.current().sendMessage(content);
    txtbox.value = '';
    btn.disabled = false;
}

function formatImMessage(totype, name, date, content) {
    /// <summary>
    /// 显示消息在消息框内
    /// </summary>
    /// <param name="totype">消息类型 css(chat-others,chat-self) </param>
    /// <param name="name">用户名</param>
    /// <param name="date">发送时间</param>
    /// <param name="content">内容</param>
    /// <returns type="">html</returns>
    var faces = content.match(/\[[^\]]+\]/g);
    
    if (faces != null)
        for (var i = 0; i < faces.length; i++)
            if ((typeof _face_img[faces[i]]) != 'undefined')
                content = content.replace(faces[i], _face_img[faces[i]]);
    
    content = content.replace(/[\r\n]/g, "<br />");
    return '<div class="' + totype + '">\
		<div class="username">' + name + '<span class="date">' +
		date + '</span></div><div class="chat">' +
		content + '</div></div>';
}

function window_setCapture(obj) {
    if (document.addEventListener) {
        document.addEventListener('mousemove', obj.onmousemove, true);
        document.addEventListener('mouseup', obj.onmouseup, true);
    } else if (obj.setCapture) {
        obj.setCapture();
    }
}

function window_releaseCapture(obj) {
    if (document.removeEventListener) {
        document.removeEventListener('mousemove', obj.onmousemove, true);
        document.removeEventListener('mouseup', obj.onmouseup, true);
    } else if (obj.releaseCapture) {
        obj.releaseCapture();
    }
}

function window_onmousedown(e) {
    var obj = document.getElementById("chat-hd");
    var imPanel = document.getElementById("_im_imPanel");
    obj.x = e.clientX - parseInt(imPanel.style.left);
    obj.y = e.clientY - parseInt(imPanel.style.top);
    obj.style.cursor = "move";
    obj.button = 1;
    window_setCapture(obj);
}

function window_onmouseup(e) {
    var obj = document.getElementById("chat-hd");
    //obj.style.cursor="default";
    obj.button = 0;
    window_releaseCapture(obj);
    setSelected(obj, true);
}

function window_onmousemove(e) {
    var obj = document.getElementById("chat-hd");
    var imPanel = document.getElementById("_im_imPanel");
    if (typeof obj.style.webkitUserSelect == "undefined")
        setSelected(obj, false);
    if (obj.button == 1) {
        if (typeof obj.style.webkitUserSelect != "undefined")
            setSelected(obj, false);
        var y = e.clientY - obj.y,
			x = e.clientX - obj.x;
        if (ie6) {
            if (y >= 0 && y < (document.documentElement.scrollTop + document.documentElement.clientHeight - parseInt(imPanel.style.height)))
                imPanel.style.top = y + "px";
            imPanelRelativeTop = y - document.documentElement.scrollTop;
        } else {
            if (y >= 0 && y < (document.documentElement.clientHeight - parseInt(imPanel.style.height)))
                imPanel.style.top = y + "px";
        }
        if (x >= 0 && x < document.body.clientWidth - parseInt(imPanel.style.width))
            imPanel.style.left = x + "px";
    } else
        if (typeof obj.style.webkitUserSelect != "undefined")
            setSelected(obj, true);
}

function setSelected(obj, flag) {
    //设置文字是否可以复制flag=true时可以复制，否则禁止复制  
    if (typeof obj.style.MozUserSelect != "undefined") //Firefox   
        /*MozUserSelect有三个值: 
		 *1.none表示所有子元素都不能被选择 
		 *2.-moz-all子元素的所有文字都可以被选择 
		 *3.-moz-none子元素的所有文字都不能选择，但input除外 
		 */
        if (flag)
            obj.style.MozUserSelect = "-moz-all";
        else
            obj.style.MozUserSelect = "none";
    else if (typeof obj.style.webkitUserSelect != "undefined") // webkit kernel
        if (flag)
            document.body.style.webkitUserSelect = "";
        else
            document.body.style.webkitUserSelect = "none";
    else if (typeof obj.onselectstart != "undefined") //IE
        obj.onselectstart = function () {
            return flag;
        };
    else //other  
        obj.onmousedown = function () {
            return flag;
        };
}

function closeImPanel() {
    /// <summary>
    /// 关闭对话div
    /// </summary>
    userSession.stop();
    var imPanel = document.getElementById("_im_imPanel");
    imPanel.style.display = "none";
}

function im_select() {
    /// <summary>
    /// 选择最新联系人，打开对话div
    /// </summary>
    /// <returns type=""></returns>
    if (userList.current() == null)
        return false;
    if (userList.current().toInfo === undefined) return false;
    if (userList.current().toInfo === null) return false;
    if (userList.current().toInfo.nickName === undefined) return false;
    var imPanel = document.getElementById("_im_imPanel");
    if (ie6) {
        imPanel.style.left = (document.body.clientWidth - parseInt(imPanel.style.width)) / 2 + "px";
        imPanel.style.top = ((window.screen.availHeight - parseInt(imPanel.style.height)) / 4 + document.documentElement.scrollTop) + "px";
        imPanelRelativeTop = (window.screen.availHeight - parseInt(imPanel.style.height)) / 4;
        window.onscroll = function () {
            imPanel.style.top = document.documentElement.scrollTop + imPanelRelativeTop;
        };
    }
    imPanel.innerHTML = formatUserInfo(userList.current().toInfo);
    imPanel.style.display = "";
}

function showImToolbar() {
    document.getElementById("_im_im-toolbar").className = "im-toolbar";
    document.getElementById("_im_tb2").style.display = "block";
    document.getElementById("_im_title").style.display = "block";
    document.getElementById("_im_status").style.display = "none";
    document.getElementById("_im_operate").style.display = "block";
}

function hideImToolbar() {
    document.getElementById("_im_im-toolbar").className = "im-toolbar im-fold";
    document.getElementById("_im_tb2").style.display = "none";
    document.getElementById("_im_title").style.display = "none";
    document.getElementById("_im_status").style.display = "block";
    document.getElementById("_im_operate").style.display = "none";
}

function onLamp(n) {
    /// <summary>
    /// 在消息状态悬浮框，显示未读消息数
    /// </summary>
    /// <param name="n"></param>
    document.getElementById("_im_status").innerHTML = '<i  class="on"></i>聊天(' + n + ')';
}

function offLamp() {
    /// <summary>
    /// 在消息状态悬浮框，还原消息数（0）
    /// </summary>
    document.getElementById("_im_status").innerHTML = '<i  class="off"></i>聊天(0)';
}

function link(id, name) {
    /// <summary>
    /// 点击网页上的im联系图片，打开对话div
    /// </summary>
    /// <param name="id"></param>
    /// <param name="name"></param>
    userList.createUser(id, name);
}

function keyDownProcess(event) {
    /// <summary>
    /// 键盘事件监控 alt+enter  换行   enter 发送
    /// </summary>
    /// <param name="event"></param>
    /// <returns type=""></returns>
    if (event.altKey && event.keyCode == 13) {
        document.getElementById("_im_textbox").value += String.fromCharCode(10);
        return true;
    } else if (event.keyCode == 13) {
        im_send(document.getElementById("_im_btnsend"));
        return false;
    }

    return true;
}

function registerImLinkEvent() {
    /// <summary>
    /// 分析网页上的连接，注册click事件
    /// </summary>
    var imLinks = document.getElementsByTagName("a");
    for (var i in imLinks) {
        var imLink = imLinks[i];
        if (imLink.className && imLink.className == "im_link") {
            imLink.onclick = imLinkClick;
        }
    }
}

function imLinkClick(e) {
    /// <summary>
    /// im图标事件处理程序
    /// </summary>
    var imLinkElement;
    if (!e) {
        if (window.event.srcElement.tagName == "IMG")
            imLinkElement = window.event.srcElement.parentElement;
        else
            imLinkElement = window.event.srcElement;
    } else {
        imLinkElement = e.currentTarget;
    }
    var href = imLinkElement.href;
    var lastIndexOf = href.lastIndexOf("/");
    var paramStr = href.substring(lastIndexOf + 1, href.length);
    var params = paramStr.split(".");
    if (params.length == 3) {
        var id = params[0];
        link(id, id);
    }
    return false;
}

var _face_img = new Array();

function initFaceImg() {
    var basePath = serverUrl+"/client/css/faces";
    _face_img["[草泥马]"] = '<img src="' + basePath + '/shenshou_org.gif" />';
    _face_img["[神马]"] = '<img src="' + basePath + '/horse2_org.gif" />';
    _face_img["[浮云]"] = '<img src="' + basePath + '/fuyun_org.gif" />';
    _face_img["[给力]"] = '<img src="' + basePath + '/geili_org.gif" />';
    _face_img["[围观]"] = '<img src="' + basePath + '/wg_org.gif" />';
    _face_img["[威武]"] = '<img src="' + basePath + '/vw_org.gif" />';
    _face_img["[熊猫]"] = '<img src="' + basePath + '/panda_org.gif" />';
    _face_img["[兔子]"] = '<img src="' + basePath + '/rabbit_org.gif" />';
    _face_img["[奥特曼]"] = '<img src="' + basePath + '/otm_org.gif" />';
    _face_img["[囧]"] = '<img src="' + basePath + '/j_org.gif" />';
    _face_img["[互粉]"] = '<img src="' + basePath + '/hufen_org.gif" />';
    _face_img["[礼物]"] = '<img src="' + basePath + '/liwu_org.gif" />';
    _face_img["[呵呵]"] = '<img src="' + basePath + '/smilea_org.gif" />';
    _face_img["[嘻嘻]"] = '<img src="' + basePath + '/tootha_org.gif" />';
    _face_img["[哈哈]"] = '<img src="' + basePath + '/laugh.gif" />';
    _face_img["[可爱]"] = '<img src="' + basePath + '/tza_org.gif" />';
    _face_img["[可怜]"] = '<img src="' + basePath + '/kl_org.gif" />';
    _face_img["[挖鼻屎]"] = '<img src="' + basePath + '/kbsa_org.gif" />';
    _face_img["[吃惊]"] = '<img src="' + basePath + '/cj_org.gif" />';
    _face_img["[害羞]"] = '<img src="' + basePath + '/shamea_org.gif" />';
    _face_img["[挤眼]"] = '<img src="' + basePath + '/zy_org.gif" />';
    _face_img["[闭嘴]"] = '<img src="' + basePath + '/bz_org.gif" />';
    _face_img["[鄙视]"] = '<img src="' + basePath + '/bs2_org.gif" />';
    _face_img["[爱你]"] = '<img src="' + basePath + '/lovea_org.gif" />';
    _face_img["[泪]"] = '<img src="' + basePath + '/sada_org.gif" />';
    _face_img["[偷笑]"] = '<img src="' + basePath + '/heia_org.gif" />';
    _face_img["[亲亲]"] = '<img src="' + basePath + '/qq_org.gif" />';
    _face_img["[生病]"] = '<img src="' + basePath + '/sb_org.gif" />';
    _face_img["[太开心]"] = '<img src="' + basePath + '/mb_org.gif" />';
    _face_img["[懒得理你]"] = '<img src="' + basePath + '/ldln_org.gif" />';
    _face_img["[右哼哼]"] = '<img src="' + basePath + '/yhh_org.gif" />';
    _face_img["[左哼哼]"] = '<img src="' + basePath + '/zhh_org.gif" />';
    _face_img["[嘘]"] = '<img src="' + basePath + '/x_org.gif" />';
    _face_img["[衰]"] = '<img src="' + basePath + '/cry.gif" />';
    _face_img["[委屈]"] = '<img src="' + basePath + '/wq_org.gif" />';
    _face_img["[吐]"] = '<img src="' + basePath + '/t_org.gif" />';
    _face_img["[打哈欠]"] = '<img src="' + basePath + '/k_org.gif" />';
    _face_img["[抱抱]"] = '<img src="' + basePath + '/bba_org.gif" />';
    _face_img["[怒]"] = '<img src="' + basePath + '/angrya_org.gif" />';
    _face_img["[疑问]"] = '<img src="' + basePath + '/yw_org.gif" />';
    _face_img["[馋嘴]"] = '<img src="' + basePath + '/cza_org.gif" />';
    _face_img["[拜拜]"] = '<img src="' + basePath + '/88_org.gif" />';
    _face_img["[思考]"] = '<img src="' + basePath + '/sk_org.gif" />';
    _face_img["[汗]"] = '<img src="' + basePath + '/sweata_org.gif" />';
    _face_img["[困]"] = '<img src="' + basePath + '/sleepya_org.gif" />';
    _face_img["[睡觉]"] = '<img src="' + basePath + '/sleepa_org.gif" />';
    _face_img["[钱]"] = '<img src="' + basePath + '/money_org.gif" />';
    _face_img["[失望]"] = '<img src="' + basePath + '/sw_org.gif" />';
    _face_img["[酷]"] = '<img src="' + basePath + '/cool_org.gif" />';
    _face_img["[花心]"] = '<img src="' + basePath + '/hsa_org.gif" />';
    _face_img["[哼]"] = '<img src="' + basePath + '/hatea_org.gif" />';
    _face_img["[鼓掌]"] = '<img src="' + basePath + '/gza_org.gif" />';
    _face_img["[晕]"] = '<img src="' + basePath + '/dizzya_org.gif" />';
    _face_img["[悲伤]"] = '<img src="' + basePath + '/bs_org.gif" />';
    _face_img["[抓狂]"] = '<img src="' + basePath + '/crazya_org.gif" />';
    _face_img["[黑线]"] = '<img src="' + basePath + '/h_org.gif" />';
    _face_img["[阴险]"] = '<img src="' + basePath + '/yx_org.gif" />';
    _face_img["[怒骂]"] = '<img src="' + basePath + '/nm_org.gif" />';
    _face_img["[心]"] = '<img src="' + basePath + '/hearta_org.gif" />';
    _face_img["[伤心]"] = '<img src="' + basePath + '/unheart.gif" />';
    _face_img["[猪头]"] = '<img src="' + basePath + '/pig.gif" />';
    _face_img["[ok]"] = '<img src="' + basePath + '/ok_org.gif" />';
    _face_img["[耶]"] = '<img src="' + basePath + '/ye_org.gif" />';
    _face_img["[good]"] = '<img src="' + basePath + '/good_org.gif" />';
    _face_img["[不要]"] = '<img src="' + basePath + '/no_org.gif" />';
    _face_img["[赞]"] = '<img src="' + basePath + '/z2_org.gif" />';
    _face_img["[来]"] = '<img src="' + basePath + '/come_org.gif" />';
    _face_img["[弱]"] = '<img src="' + basePath + '/sad_org.gif" />';
    _face_img["[蜡烛]"] = '<img src="' + basePath + '/lazu_org.gif" />';
    _face_img["[钟]"] = '<img src="' + basePath + '/clock_org.gif" />';
    _face_img["[话筒]"] = '<img src="' + basePath + '/m_org.gif" />';
    _face_img["[蛋糕]"] = '<img src="' + basePath + '/cake.gif" />';
}

function main() {
    /// <summary>
    /// 页面Onload事件，im需要处理的方法
    /// </summary>
    initFaceImg();
    var uidElement = document.getElementById("im_uid");
    if (uidElement != null)
        userSession.id = uidElement.innerHTML;
    userSession.init();

    addStyle(serverUrl + "/client/css/im.css?v=0.5");
    createImToolbar();
    createImPanel();
    offLamp();
    registerImLinkEvent();
}

if (window.attachEvent)
    window.attachEvent("onload", main);
else if (window.addEventListener)
    window.addEventListener("load", main);
else
    window.onload = main;
