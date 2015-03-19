### 使用Bean验证器 

验证来自用户的输入数据来维护数据的一致性是应用逻辑中的重要的一部分,数据的验证可以在不同的层次上进行.
Java EE6中带来了一个新的验证模型 JavaBean 验证,该模型通过对类,属性,方法进行注释的方式来实现约束.
至于约束条件可以使用内置的,也可以进行自定义的约束.

在此先介绍以下标准的内置Bean验证器.
```
|验证约束           	| 描述                    	|样例    |	 
|----------------------	|:-----------------------------:| ------:|	
|@AssertFalse	    	|				|	 |       
|@AssertTrue		|				|	 |       
|@DecimalMax		|				|	 |       
|@DecimalMin		|				|	 |       
|@Digits		|				|	 |       
|@Future		|				|	 |       
|@Max			|				|	 |       
|@Min			|				|	 |       
|@NotNull		|				|	 |       
|@Null			|				|	 |       
|@Past			|				|	 |       
|@Pattern		|				|	 |       
|@Size                	|属性值的长度			|	 |       	

```




### 标准验证器

|   标准验证器类        |     标准验证器标签            |        功能                          |
| --------------------- |:-----------------------------:| ------------------------------------:|
|BeanValidator          |validatorBean                  |为组件注册一个bean验证器              |
|DoubleRangeValidator   |validateDoubleRange            |检验局部值是否在某个值区间内          |
|LengthValidator        |validateLength                 |检验组件的值是否在某个长度范围内      |
|LongRangeValidator     |validateLongRange              |检验组件的值是否在某个长度范围内      |
|RegexValidator         |validateRegx                   |检验组件的值是否匹配某个正则表达式    |
|RequiredValidator      |validateRequired               |确保组件的值部位空                    |




### 编写方法进行验证


```
public void validateNumberRange(FacesContext context,
                UIComponent toValidate,
                Object value) {
    if (remainingGuesses <= 0) {
        FacesMessage message = new FacesMessage("No guesses left!");
        context.addMessage(toValidate.getClientId(context), message);
        ((UIInput) toValidate).setValid(false);
        return;
    }
    int input = (Integer) value;
    if (input < minimum || input > maximum) {
        ((UIInput) toValidate).setValid(false);
        FacesMessage message = new FacesMessage("Invalid guess");
        context.addMessage(toValidate.getClientId(context), message);
    }
}
```
