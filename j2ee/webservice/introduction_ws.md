## Web Service Type ##

    JAX-WS
    JAX-RS


### RESTful Web Service (JAX-RS) ###

    "Representatinal State Transfer"

    **data and functionality are considered resources** 
    **access** using URIs

    a client/server architecture
    use stateless communication protocol-HTTP
    using standardized interface and protocol to exchange resources's 


#### Features (simple, lightweight, fast) ####

    1. Resource Identification through URI

    2. Uniform interface: resources are manipulated using a fixed set
       of four create, read, update, delete operations: PUT,GET, POST,
       DELETE.
            PUT: creates a new resource
            DELETE: deletes the resource created by PUT
            GET: retrieves teh current state of a resource
            POST: transfers a new state onto a resource

    3. Self-descriptive messages: resources are decoupled from their 
       representation, so their content can be accessed in a variety 
       of forms.
        
    4. Stateful interactions through hyperlinks: Every interaction with 
       a resource is stateless, that is, request messages are self-contained.
       Stateful interactions are based on the concept of explicit state 
       transfer.(Several techniques exist to exchange state,URI rewriting,
       cookies and hidden form field.)
       State can be embedded in response mesages to point to valid future 
       states of teh interaction.
        

