'''
Created on Jul 10, 2015

@author: liang.guisheng
'''
from pydoc import classname

# the models will used to store the data 

# the class declaration
class TClass:
    def __init__(self, id, name):
        self.id = id
        self.name = name
        self.methods = []
    
    def add_method(self, methodId):
        self.methods.append(methodId)
        

# the method declaration
class TMethod:
    def __init__(self, id, name, *fields):
        self.id = id 
        self.name = name 
        self.invoked = []
        self.parameters = []
        self.localVars = []
        self.fields = fields
    
    def add_invoked(self, invokedId):
        self.invoked.append(invokedId)
    
    def add_parameter(self, parameter):
        self.parameters.append(parameter)
    
    def add_localVar(self, localVar):
        self.localVars.append(localVar)
    
        
# the variable declaration 
class TVariable:
    def __init__(self, varType, varName):
        self.varType = self.__parseVarType(varType)
        self.varName = varName
        
    # used to process some special variable type
    def __parseVarType(self, varType):
        return varType
        
    
class Context:
    
    def __init__(self, name):
        self.name = name
        
    def name(self):
        return self.name
        
        
    contexts = {
              "package":{"start":"package", "end":";"},
              "import":{"start":"import", "end":";"},
              "class":{"start":"class", "end":"}"},
              "annotation":{"start":"@"},
              "static":{"start":"static", "end":"}"},
              "method":{"start":"method_regex", "end":"}"},
              "string":{"start":"\"", "end":"\""},
              "if":{"start":"if", "end":"}"},
              "elseif":{"start":"else if", "end":"}"},
              "else":{"start":"else", "end":"}"},
              "for":{"start":"for", "end":"}"},
              "while":{"start":"while", "end":"}"},
              "switch":{"start":"switch", "end":"}"},
              "try":{"start":"try", "end":"}"},
              "catch":{"start":"catch", "end":"}"},
              "finally":{"start":"finally", "end":"}"},
              "invocation":{"start":"regex", "end":"}"},
                "statement":{"start":"regex", "end":";"}
            }
        
        
    # context can be one of the followings:
    '''
    package
    import
    annotation
    class
    statement  **Common 
        variable declaration/assignment/create object statement
        return statement
        break statement
        case statement
        continue statement
        method invocation:local method/var's method
        expression
    string   
    staticBlock
    methodDeclaration
    methodBlock
    ifStatement
    ifBlock
    elseifStatement
    elseifBlock
    elseBlock
    forStatement
    forBlock
    tryStatement
    tryBlock
    catchStatement
    catchBlock
    finallyBlock
    whileStatement
    whileBlock
    switchStatement
    switchBlock
    '''
 
    
    
        
        
    
# a stack to store the whole content of java file
class MainStack:
    content = []
    
    def __init__(self):
        pass
    
    def push(self, string):
        self.content.append(string)
        
    def pop(self):
        return self.content.pop()
    
    def is_empty(self):
        return len(self.content) == 0
    
    def last(self):
        if len(self.content) > 1:
            return self.content[len(self.content) - 1]
        else:
            return None
    
# a stack to store Contexts when do parsing
class ContextStack:
    contexts = []
    
    def __init__(self):
        pass 
    
    def push(self, context):
        self.contexts.append(context)
    
    def pop(self):
        return self.contexts.pop()

    def last(self):
        
        if len(self.contexts) > 1:
            return self.contexts[len(self.contexts) - 2]
        else:
            return None
    
    def current(self):
        if len(self.contexts) >= 1:
            return self.contexts[len(self.contexts) - 1]
        else:
            return None
    
# a stack to store temp statement
class TempStack:
    content = []
    
    def __init__(self):
        pass
    
    def push(self, string):
        self.content.append(string)
        
    def pop(self):
        return self.content.pop()
    
    def __str__(self):
        return " ".join(self.content)


class UnresolvedInvocation:
    currentMethodId = None
    className = None
    methodName = None
    parameters = []
    
    def __init__(self, currentMethodId, className, methodName, *parameters):
        self.currentMethodId = currentMethodId
        self.className = className
        self.methodName = methodName
        self.parameters = parameters
    

# global variables
classId = 0
methodId = 0
classes = []
methods = []
unresolvedInvocations = []

