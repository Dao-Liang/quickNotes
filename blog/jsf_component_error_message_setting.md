
### Custome JSF Converter,Invalidate Error Message ###

#### component attirbutes

##### required attribute 
```
javax.faces.component.UIInput.REQUIRED_detail=this field value can not be null

```
#### Converters

##### Double Converter
```
javax.faces.converter.DoubleConverter.DOUBLE_detail=''{2}'': ''{0}'' should be number

```
##### DateTimeConverter

```
javax.faces.converter.DateTimeConverter.DATE={2}: ''{0}'' could not be understood as a date.
javax.faces.converter.DateTimeConverter.DATE_detail={2}: ''{0}'' could not be understood as a date. Example: {1}
```
#### Validators

##### DoubleRangeValidator error message
```
javax.faces.validator.DoubleRangeValidator.MINIMUM_detail=''{1}'' must less than ''{0}''
javax.faces.validator.DoubleRangeValidator.MAXIMUM_detail
javax.faces.validator.DoubleRangeValidator.NOT_IN_RANGE

```
##### LengthValidator
```
javax.faces.validator.LengthValidator.MAXIMUM={1}: Validation Error: Length is greater than allowable maximum of ''{0}''
javax.faces.validator.LengthValidator.MINIMUM={1}: Validation Error: Length is less than allowable minimum of ''{0}''

```
