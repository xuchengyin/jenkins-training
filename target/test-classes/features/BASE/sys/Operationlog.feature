@BASE
Feature: BASE_操作日志_Operationlog

  @Smoke
  @Debug
  Scenario Outline: Operationlog_查询操作日志
    When 根据查询条件起始时间"<startLogTime>",终了时间"<endLogTime>",操作用户"<userName>",操作描述"<opDesc>",模块选择"<opNames>"来查询日志
    Then 成功根据查询条件起始时间"<startLogTime>",终了时间"<endLogTime>",操作用户"<userName>",操作描述"<opDesc>",模块选择"<opNames>"来查询日志

    Examples: 
      | common | startLogTime   | endLogTime     | userName | opDesc       | opNames       |
      | 空查询    |                |                |          |              |               |
      | 一个条件查询 | 2 | 系统时间 |          |              |               |
      | 全条件查询    | 1 | 系统时间  | %管理员%    | %通过ciIds查询%  | DMV, DCV, PMV |
      | 两个条件查询 | 3 | 系统时间 | %管理员%    |              |               |
      | 三个条件查询 | 4 | 系统时间 | %管理员%    | %查询系统默认图标%   |               |
      | 一个模块查询 | 5 | 系统时间 | %管理员%    | %通过ciIds查询%  | DMV           |      
      | 特殊字符   | 6 | 系统时间 | %管理员$#@% | %通过ciIds$#@% | DMV           |

