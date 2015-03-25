### Qua Mar 25 13:49:23 BRT 2015: ###

Hoje ao visualizar um fundo na tela Fundos, aparece **appears** uma tela com a série **series** de cotas **quotas**. Vamos tornar **render** essa tela mais útil **helpful** e mais "apresentável **presentable**" pro usuário.

- Tirar **take away** a série de cotas e colocá-la **put it** em outra tela, acessível **available** por um link "Série" no menu superior da tela do fundo.
- 去掉一系列的cotas并将其放在另外的页面上,可以使用一个链接"Série"来进行导航.

Na tela de entrada **entrance** do Fundo, mostrar **show**:
在进入Fundo的页面中,显示以下内容:
- Categorias vigentes **present,actual** (em todas as fontes), administrador e gestor vigentes.
- 当前对于每个Source的Categories,administrador,gestor

- Pizza com a carteira **wallet,purse** CVM (a mesma que aparece em "Carteira CVM"). Mostrar apenas a pizza. Se o usuário desejar **wish,want** ver a lista de ativos **assets** , ele acessa a página "Carteira CVM".
- 使用饼图来显示钱包(类似于"Carteira CVM"链接),只显示一个pizza图. 如果用户想要查看资产的列表,就访问"Carteira CVM"页面.
- Mostrar gráfico **graphic,diagram** com a série de cota (QuotaDao.findBetweenDatesAdjusted), dos últimos **last,final** 252 valores (um ano). Nesse gráfico colocar tb (se houver) o benchmark indicado na categoria Risk Office do fundo.
- 使用一系列的cota来进行图形显示,最后的252个值.该图形放置一个表

Suporte **holder**, se tiverem **have** mais alguma sugestão pra tela... digam **say**.


Essa modificação provavelmente **probable** vai quebrar testes do rosys-html-testing, verificar antes do commit.
