Constants 通用常量<br/>
MaQiaoStringBuilder jvm外内存存储char，只支持(++=)，不提供(+-=)<br/>
MaQiaoBeanFieldsOffset 某类Class中的各属性的类型、地址偏移量，以供unsafe读取<br/>
MaQiaoToJson 综合 把bean转成Json，主要是通过MaQiaoBeanFieldsOffset提取对象属性的偏移量，以unsafe.getInt()提取<br/>
以MaQiaoStringBuilder进行在jvm外进行保存!!!<br/>
主要是学习一下属性的反射，偏移地址和unsafe的使用常用方法的使用!!!<br/>
<br/>