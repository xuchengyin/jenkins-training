@CMV
Feature:CMV_树状图

	@deleteTpl
	Scenario:DynamicClass_保存_查询
		
	When 查询ci属性,用如下参数验证:
	|levelName5|日期|
	|levelName4|枚举|
	|levelName3|小数|
	|levelName2|整数|
	|levelName1|CI Code|
	
	When 用以下参数创建树状图:
	|dataStatus|tplName|levelNameList|ciClassNames|
	|1|测试树状图|CI Code:整数:小数:枚举:日期|s@&_-:Application:Cluster|
	
	Then 用以下参数验证树状图创建成功:
	|dataStatus|tplName|levelNameList|ciClassNames|
	|1|测试树状图|CI Code:整数:小数:枚举:日期|s@&_-:Application:Cluster|
	
	Then 重新构建默认树状图
	
	Then 查询默认动态分类配置,用以下数据验证:
	|level-1|level-2|level-3|level-4|level-5|level-6|
	|six|1|0.0000|""|""|level-6|
	
	Then 删除默认树状图"1"个

	When 用以下参数创建树状图,创建失败:
	|dataStatus|tplName|levelNameList|ciClassNames|kw|
	|1|~~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}方大家反馈~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《~！@#￥%！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}方大家反馈~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}~！@#￥%……&*（）——啊护发yuiop+·-=sdjfajfasjfadf a打卡机法拉盛解放东路卡视角打发时间地方大家反馈垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《》；‘，。、123456阿萨德覆盖号就’？】}垃圾是大法师大姐夫拉克丝的减肥啦{【aa、：“”《~！@#￥%|CI Code:整数:小数:枚举:日期|s@&_-|Data truncation: Data too long for column 'TPL_NAME' at row 1|

	