# SUSTC_HaiYangXiWeb
南科大海洋系官网，简版

## 参考Demo

#### 页面注入参考：Controller/PageController.java   （页面查看）
建议：
前台页面手动写死，根据文章id进行页面浏览。如：http://xxx.com/2.html 表示某一分类的页面（eg. 人才招聘），但是进入该分类应该是进入该分类中某一篇文章才对，所以会自动跳转到：http://xxx.com/article/372.html ，所以访问 /2.html 和 /article/372.html 是同一个页面。
对于某一篇文章的页面：分类信息写死即可（可以参考 thymeleaf th:include 用法，进行页面之间的模块化统一拼接，如 header、footer 信息）；内容信息使用 Article（详情 model，包含文章具体内容信息） 和 Articles（简介 model，包含文章 title、category...等信息）两个 model 进行注入，可以参考 PageController.java 内具体的代码实现
#### API 参考：   Controller/ArticleController.java （文章增删查改）
该类提供了对文章的所有操作方法（RESTful），一般是给管理员用的。前期建议让系里给文章内容，自己搞定填完数据，把前端页面匹配上对应的文章 id，之后再根据实际情况开发管理员端进行维护文章信息。

## update 2017/12/18
