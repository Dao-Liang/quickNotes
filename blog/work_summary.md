## EntityManager instance ##
========================================


### in ManagedBean ###
	can use both seam EntityManager object and Hibernate object


### in EJB ###
	should use EntityManager object to do persist works


## Log ##
========================================

### log for exceptions ###
	log for exeptions should use log.error to log exception informations



## Correct an already big method ##
========================================


### create new method and invok it ###



## DB Queries ##
========================================


### queries for Entities of JPA ###
	write the query statement directly into the JPA Entity class and create a namedQuery to invok it



## Class Types ##
========================================


### Pojo ###
	a class has no annotations applied to it, the class get resource handles from other class and does work with that.
	

### EJB ###
	class with EJB annotations, have transaction control and using EntityManager from Hibernate


### Seam ###
	class with Seam annotations, and also have transaction controli and can use EntityManager from Seam Context and Hibernate Context
