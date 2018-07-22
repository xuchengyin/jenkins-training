@BASE
Feature: BASE_角色管理_Auth

  @Smoke @CleanRole
  Scenario Outline: Auth_新建角色、更改角色、删除角色
    When 创建角色名称为"<roleName>"，角色描述为"<roleDesc>"的角色
    Then 系统中存在角色名称为"<roleName>"，角色描述为"<roleDesc>"的角色
    When 给角色名称为"<roleName>"的角色添加如下功能权限:
      | 功能权限   | 权限id | 子菜单    |
      | 对象模型   |  660 |        |
      | 关系模型   |  661 |        |
      | 图标管理   |  662 |        |
      | 数据中心列表 |  705 |        |
      | 全局资源管理 |  707 |        |
      | 值班大屏   |  980 | ticket |
    Then 角色名称为"<roleName>"的角色成功添加功能权限如下:
      | 功能权限   | 权限id | 子菜单    |
      | 对象模型   |  705 |        |
      | 关系模型   |  660 |        |
      | 图标管理   |  661 |        |
      | 数据中心列表 |  707 |        |
      | 全局资源管理 |  662 |        |
      | 值班大屏   |  980 | ticket |
    When 角色名称为"<roleName>"的角色修改角色名称为"<updateRoleName>"，角色描述为"<updateRoleDesc>"的角色
    Then 角色名称为"<updateRoleName>"，角色描述为"<updateRoleDesc>"的角色修改成功
    When 删除角色名称为"<updateRoleName>"的角色
    Then 系统中不存在角色名称为"<updateRoleName>"的角色

    Examples: 
      | common | roleName     | roleDesc          | updateRoleName | updateRoleDesc |
      | 正常数据   | 角色sun222222  | role2345          | 修改后角色名称        | 修改后角色描述        |
      | 特殊字符   | 角色名称1111$#@! | 角色描述1111$#@! | 修改后名称          | 修改后角色描述123     |
      | 角色名称_中文最大20个字符   | 测试角色名称为最大中文字符二十个汉字测试 | 角色描述 | 测试角色名称为最大中文字符二十个汉字测试          | 角色描述最大200中文字符.txt     |
      | 修改角色描述_中文最大200个字符   | 测试角色描述最大字符| 角色描述最大200中文字符.txt| 测试角色描述最大字符          |   描述   |

  Scenario Outline: Auth_角色搜索
    When 创建角色名称为"<roleName>"，角色描述为"<roleDesc>"的角色
    And 搜索角色名称包含"<searchKey>"的角色
    Then 包含"<searchKey>"关键字的的角色全部搜索出来
    When 删除角色名称为"<roleName>"的角色
    Then 系统中不存在角色名称为"<roleName>"的角色

    Examples: 
      | common   | roleName      | roleDesc | searchKey   |
      | 角色名称全匹配  | 角色sun222333   | role2345 | 角色sun222333 |
      | 角色名称部分匹配 | 角色名称%1111$#@! | 角色描述123  | 角色名称%       |

  @Smoke
  Scenario: Auth_取得功能权限
    When 取得所有的功能权限
    Then 成功取得所有的功能权限"系统配置,CMV,DMV,EMV,数据中心管理,GIV,PMV"
  @Debug  
  Scenario: Auth_角色管理_数据权限_新增
    When 给角色"admin"的分类"Application,Cluster,s@&_-"设置新增权限
    Then 分类"Application,Cluster,s@&_-"的权限为新增权限
    When 添加ci窗口中查询"Application,Cluster,s@&_-"分类
    Then 添加ci窗口中能正确查询出"Application,Cluster,s@&_-"分类
    And 删除角色"admin"的数据设置