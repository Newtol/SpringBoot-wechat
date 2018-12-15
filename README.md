# SpringBoot-wechat
SpringBoot开发微信公众号后台（可同时为多个公众号提供服务，非第三方平台）

## 配置使用

### 接入微信服务器
1. 将application.yml的weiXin.token更改为在微信服务器配置的token，如果需要服务于多个公众号，将每个公众号的token设置为一致即可接入

### 网页授权
实现为不同公众号分为提供网页授权和跳转服务
1. 将在微信服务器将网页授权的跳转地址和application.yml中weiXin.getWebAuthorize的设置为：`http://xxx/xxx/getWebAuthorize`
2. 请求`http://xxx/xxx/setRedirectUrl`设置需要跳转的链接,参数为appId,appSecret,redirectUrl，获取返回的state
3. 将第二步获取到的state添加`http://xxx/redirectUrl?state=STATE`中，将该链接设置为自动回复或者菜单中，即可实现网页授权和跳转

### 获取AccessToken
1. 请求`http://xxx/accessToken`,即可获得accessToken,当一个公众号有多个子项目运行时，可以避免accessToken冲突

### 自定义菜单实现

1. 在application.yml中按照yml格式进行菜单编辑，例如:
```yml
menu:
  button:
    - sub_button:
      - clickButton:
        type: click
        name: 点击
        key: hi
      - clickButton:
        type: click
        name: 点击2
        key: hi1
      name: 视图
    - sub_button:
      - clickButton:
        type: view
        name: 视图2
        url: http://www.baidu.com
      name: 菜单
```
2. 重启项目后，请求：`http://xxx//menu`,菜单即可生效

### 自动回复
1. 请求`http://xxx/setAutoMessage`,设置自动回复的内容（支持回复语音、图片、文字、图文、视频等）例如：
```json
{
"fromUserName":"你的微信公众号账号",
"key":"图文",
"msgType":"news",
"articleCount":2,
"articles":{
	"item":[{
		"title":"test",
		"description":"这是测试内容",
		"picUrl":"http://hongyan.cqupt.edu.cn/images/index_top.jpg",
		"url":"http://hongyan.cqupt.edu.cn/"
		
	},{
		"title":"test2",
		"description":"这是测试内容3",
		"picUrl":"http://www.hers.cn/uploadfile/2011/1006/20111006022157183.jpg",
		"url":"http://mp.weixin.qq.com/mp/appmsg/show?__biz=MjM5MDE4Njg2MQ==&appmsgid=10000072&itemidx=1&sign=bea6deb75836dbe1249dcf394e8f3c21#wechat_redirect"
	}]
}
```

