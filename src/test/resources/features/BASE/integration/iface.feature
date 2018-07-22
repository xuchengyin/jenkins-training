@BASE
Feature: BASE_数据集成_Iface

    @Smoke
	Scenario Outline: iface_增加数据源,搜索数据源
		When 给模板名称为"zabbix"增加数据源为"<ifaceName>"
		Then 系统中存在"<ifaceName>"数据源
		When 搜索名称包含"<searchKey>"数据源
		Then 名称包含"<searchKey>"的数据源全部搜索出来
		When 删除名称为"<ifaceName>"数据源
	Examples:
	  | common     | ifaceName       |searchKey|
      | 中英文数字   |数据源YYYY1111111|数据源YYY|
      | 特殊字符      |数据源￥%##@|数据源￥%##@|
      | 最大长度 |数据源数据源数据源YYTTTUUIII876543^%$#@@!|数据源数据源数据源YYTTTUUIII876543^%|
