'''
Created on Jul 10, 2015

@author: liang.guisheng
'''
from models import Context, MainStack, ContextStack , TempStack

javaFile = "/home/local/RISKOFFICE/liang.guisheng/ROWorkspace/rosys-core/src/core/br/com/riskoffice/funds/ui/web/report/express/FundExpressSheetXmlLoader.java"

replaceChars = ";(){},\""
modifiers = ["public", "private", "protected", "static", "final"]
mainStack = MainStack()
contextStack = ContextStack()
tempStack = TempStack()
statementsEndDelimiters = "};)\""
isInString = False
 
# used to remove comments and split some special chars and got the string list content
def cleanFile(filename):
    
    content = []
    isInMultiple = False
    lines = open(filename, "r").readlines()

    for line in lines:
        line = line.lstrip()
        line = line.strip('\n\r')
        # single line comment or documentation
        if line.startswith("/*") and line.endswith("*/"):
            print "single comment or documentation"
            continue
        
        # entering multiple comment or documentation
        if line.startswith("/*") and not line.endswith("*/"):
            isInMultiple = True
            continue
        
        if isInMultiple and not line.endswith("*/"):
#             print line
            continue
        elif isInMultiple and line.endswith("*/"):
            isInMultiple = False
            continue
            
            
        # single line
        if line.startswith("//"):
            continue 
        
        if not line.startswith("//") and "//" in line and line.find("//") > line.find("\""):
            line = line[0:line.find("//")]
            
      
        for c in replaceChars:
            line = line.replace(c, " " + c + " ")

        for word in line.split(" "):
            if len(word.strip()) > 0:
                content.append(word)
                
            
        
    
#    for line in content:
#        print line

    return content


# parsing the file content
def parse(content):

    context = None
    currentContext = None
    lastContext = None
        
    for index in range(len(content)):

        word = content[index]
        mainStack.push(word)
        
        if word == "else":
            concateWord = word + content[index + 1]

            if concateWord == "elseif":
                context = Context("elseifBlock")
            elif concateWord == "else{":
                context = Context("elseBlock")
        
        if word == "static":
            concateWord = word + content[index + 1]
            
            if concateWord == "static{":
                context = Context("staticBlock")
        
        if word == "package":
            context = Context("package")
            
        if word == "import":
            context = Context("import")
            
        if word == "class":
            context = Context("class")
            
        if word.startswith("@"):
            context = Context("annotation")
            
        if word == "if":
            context = Context("ifBlock")
            
        if word == "while":
            context = Context("whileBlock")
            
        if word == "for":
            context = Context("forBlock")
            
        if word == "switch":
            context = Context("switchBlock")
            
        if word == "try":
            context = Context("tryBlock")
            
        if word == "catch":
            context = Context("catchBlock")
            
        if word == "finally":
            context = Context("finallyBlock")
            
        currentContext = contextStack.current()
        # '(' has lots of meanings    
        if word == "(":
            if currentContext.name == "ifBlock":
                context = Context("ifStatement")
                
            elif currentContext.name == "elseifBlock":
                context = Context("elseifStatement")
                
            elif currentContext.name == "forBlock":
                context = Context("forStatement")
                
            elif currentContext.name == "whileBlock":
                context = Context("whileStatement")
                
            elif currentContext.name == "switchBlock":
                context = Context("switchStatement")
            
            elif currentContext.name == "catchBlock":
                context = Context("catchStatement")
            
            elif currentContext.name == "annotation":
                context = Context("annotationStatement")
            else:
                context = methodCheck()
            
        
        if context != None:
            contextStack.push(context)
            currentContext = context
            
        
            

        
        

# parse the word the determine if it can be used to determine the context
def beginFilter(word):

    global  isInString
    
    context = None
    lastContext = contextStack.last()
    
    if word == "package":
        context = Context("package")
    
    if word == "import":
        context = Context("import")

    if word.startswith("@"):
        context = Context("annotation")
    
    if word == "class":
        context = Context("class")
    
    if word == "static{":
        context = Context("staticBlock")

    if word == "\"" and isInString:
        isInString = not isInString
        return 
    
    if word == "\"" and not isInString:
        isInString = not isInString
        context = Context("string")
    
    if word == "if":
        context = Context("ifBlock")

    if word == "elseif":
        context = Context("elseifBlock")
    
    if word == "else{":
        context = Context("elseBlock")

    if word == "for":
        context = Context("forBlock")

    if word == "while":
        context = Context("whileBlock")

    if word == "switch":
        context = Context("switchBlock")

    if word == "try":
        context = Context("tryBlock")

    if word == "catch":
        context = Context("catchBlock")

    if word == "finally":
        context = Context("finallyBlock")

    if word == "(":
        if lastContext != None and lastContext.name == "ifBlock":
            context = Context("ifStatement")
        if lastContext != None and lastContext.name == "forBlock":
            context = Context("forStatement")
        if lastContext != None and lastContext.name == "switchBlock":
            context = Context("switchStatement")
        if lastContext != None and lastContext.name == "catchBlock":
            context = Context("catchStatement")
        if lastContext != None and lastContext.name == "elseifBlock":
            context = Context("elseifStatement")
        if lastContext != None and lastContext.name == "whileBlock":
            context = Context("whileStatement")
        
        
    if context == None and lastContext != None and lastContext.name == "statement":
        return
    # last context none-exists or exists bug not statement
    if context == None and (lastContext == None or lastContext != None and lastContext.name != "statement"):
        context = Context("statement")

    if context.name == "statement" and lastContext != None and lastContext.name != "statement":
        return
    
#     print context.name
    contextStack.push(context)
         
# filter the current word
def filterWord(word):
    
    context = contextStack.current()

    if context == None:
        return 

    if word == ";":
        simpleStatementHandler() 
    
    if word == "\"" and isInString:
        stringStatementHandler() 


    if word == ")":
        if context.name == "annotation":
            annotationStatementHandler()
        
        if  context.name == "method":
            methodDeclarationStatementHandler()
    
        if  context.name == "invocation":
            methodInvocationStatementHandler()
        
    
    
    if word == "}":
        if context.name == "class":
            classStatementHandler()
    
        if  context.name == "statment":
            statementBlockHandler()
        
        if  context.name == "static":
            staticBlockHandler()
    
        if  context.name == "method":
            methodBlockHandler()
    
        if  context.name == "if":
            ifBlockHandler()
        
        if  context.name == "elseif":
            elseifBlockHandler()

        if  context.name == "else":
            elseBlockHandler()

        if  context.name == "for":
            forBlockHandler()
    
        if  context.name == "while":
            whileBlockHandler()
        
        if  context.name == "switch":
            switchBlockHandler()

        if  context.name == "try":
            tryBlockHandler()

        if  context.name == "catch":
            catchBlockHandler()

        if  context.name == "finally":
            finallyBlockHandler()

    

# check method declaration
def methodCheck():
    non_method_declaration_words = [
                                  "=", "{", "}", "new", "if", ";", "=", "."
                                  ]
    content = []
    while True:
        if mainStack.is_empty():
            break;
        word = mainStack.pop()
        
        if word in non_method_declaration_words  :
            mainStack.push(word)
            break
        else:
            content.insert(0, word)
    
    import re
    methodP = re.compile(r"(public|private|protected)?\s*(static)?\s*([\w\[\]<>]+)\s([\w]+){1}\s\(")
    result = methodP.match(" ".join(content))        
    if result :
        print " ".join(content)
        context = Context("methodBlock")
    else:
        context = None
    return context
def forBlockHandler():
    pass

def whileBlockHandler():
    pass

def switchBlockHandler():
    pass

def tryBlockHandler():
    pass

def catchBlockHandler():
    pass

def finallyBlockHandler():
    pass


def elseBlockHandler():
    pass

    
def elseifBlockHandler():
    pass
        
def ifBlockHandler():
    pass

def methodBlockHandler():
    pass
        
        
def staticBlockHandler():
    pass
        
def statementBlockHandler():
    pass
        
def classStatementHandler():
    pass
        
def methodInvocationStatementHandler():
    pass
        
def methodDeclarationStatementHandler():
    pass
        
def annotationStatementHandler():
    pass
    
def simpleStatementHandler():
    statement = []
    
    while True:
        if mainStack.is_empty():
            break;
        
        word = mainStack.pop()
        if word == "{" or word == "}" :
            mainStack.push(word)
            break
        else:
            statement.insert(0, word)
        
    print " ".join(statement)
    
    
        
def stringStatementHandler():
    pass

if __name__ == '__main__':
    content = cleanFile(javaFile)
    parse(content)
