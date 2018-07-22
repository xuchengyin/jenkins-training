@DMV @Smoke
Feature: DMV_一键导入导出

  Scenario: MigrateDiagram_一键导出
    When 一键导出视图
    Then 成功导出所有视图

 #会清除所有视图， 暂时注释掉
  #Scenario Outline: MigrateDiagram_一键导入
    #When 一键导入视图文件"<fileName>"
    #Then 成功导入视图文件"<fileName>"
#
    #Examples: 
      #| common | fileName     |
      #| 一键导入   | diagrams.zip |
