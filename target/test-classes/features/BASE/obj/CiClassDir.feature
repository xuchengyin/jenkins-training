@BASE
Feature:BASE_对象管理_ciClass目录

	@Smoke
	@CleanCiClsDir
	Scenario Outline:CiClass目录_增删改
		When 创建名称为<ciClassName>的CiClass目录
		Then 系统中存在名称<ciClassName>的CiClass目录
		And 再次创建名称为<ciClassName>的CiClass目录失败,kw="is exists code"
		When 修改CiClass目录<ciClassName>的名称为<ciClassNameModify>
		Then 目录名称<ciClassName>更新为<ciClassNameModify>
		When 删除名称为<ciClassNameModify>的CiClass目录
		Then 系统中不存在名称为<ciClassNameModify>的CiClass目录
		
		Examples:字符类型校验
		|case|ciClassName|ciClassNameModify|
		|中英文|中ing1|修改后|
		|空字符串|\"\"|!@#$%^&*()_+|
		|特殊字符|！@#￥%……&*（——-={【:：\}\|、.《|中|
		|最大长度|只能123456ing只能123456i|只新建分类目录名称新建分类目录名称新中文|
		|超过最大页面长度20汉字|新建分类目录名称新建分类目录名称新建分|新建分类目录名称新建分类目录名称新建分|
		
	@Smoke
	@CleanCiClsDir
	@Debug
	Scenario Outline:CiClass目录_增加失败的场景
		When 创建名称为"<ciClassDirName>"的CiClass目录失败,kw="<kw>"
		Examples:目录名称不对
		|case|ciClassDirName|kw|
		#|超过最大数据库长度40, oracle和mysql返回值不一样所以去掉|只能123456ing只能123456i3只能123456ing只能123456i3只能123456ing只能123456i3只能123456ing只能123456i3|Data truncation: Data too long for column 'DIR_NAME' at row 1|
		|名字为空||the 'record.dirName' is empty argument!|
		|名字已经存在|业务领域|is exists code '业务领域'!|

	
	