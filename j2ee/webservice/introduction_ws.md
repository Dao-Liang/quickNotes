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
        
#### Annotations for JAX-RS ####

    @Path 
    The @Path annotation’s value is a relative URI path indicating where the Java class will
    be hosted: for example, /helloworld. You can also embed variables in the URIs to
    make a URI path template. For example, you could ask for the name of a user and pass
    it to the application as a variable in the URI: /helloworld/{username}.

    @GET 
    The @GET annotation is a request method designator and corresponds to the similarly
    named HTTP method. The Java method annotated with this request method
    designator will process HTTP GET requests. The behavior of a resource is determined
    by the HTTP method to which the resource is responding.

    @POST 
    The @POST annotation is a request method designator and corresponds to the similarly
    named HTTP method. The Java method annotated with this request method
    designator will process HTTP POST requests. The behavior of a resource is
    determined by the HTTP method to which the resource is responding.

    @PUT 
    The @PUT annotation is a request method designator and corresponds to the similarly
    named HTTP method. The Java method annotated with this request method
    designator will process HTTP PUT requests. The behavior of a resource is determined
    by the HTTP method to which the resource is responding.

    @DELETE 
    The @DELETE annotation is a request method designator and corresponds to the
    similarly named HTTP method. The Java method annotated with this request method
    designator will process HTTP DELETE requests. The behavior of a resource is
    determined by the HTTP method to which the resource is responding.

    @HEAD 
    The @HEAD annotation is a request method designator and corresponds to the similarly
    named HTTP method. The Java method annotated with this request method
    designator will process HTTP HEAD requests. The behavior of a resource is
    determined by the HTTP method to which the resource is responding.

    @PathParam 
    The @PathParam annotation is a type of parameter that you can extract for use in your
    resource class. URI path parameters are extracted from the request URI, and the
    parameter names correspond to the URI path template variable names specified in the
    @Path class-level annotation.

    @QueryParam 
    The @QueryParam annotation is a type of parameter that you can extract for use in your
    resource class. Query parameters are extracted from the request URI query parameters.
    
    @Consumes
    The @Consumes annotation is used to specify the MIME media types of representations
    a resource can consume that were sent by the client.

    @Produces
    The @Produces annotation is used to specify the MIME media types of representations
    a resource can produce and send back to the client: for example, "text/plain".

    @Provider
    The @Provider annotation is used for anything that is of interest to the JAX-RS
    runtime, such as MessageBodyReader and MessageBodyWriter. For HTTP requests,
    the MessageBodyReader is used to map an HTTP request entity body to method
    parameters. On the response side, a return value is mapped to an HTTP response entity
    body by using a MessageBodyWriter. If the application needs to supply additional
    metadata, such as HTTP headers or a different status code, a method can return a
    Response that wraps the entity and that can be built using
    Response.ResponseBuilder.   
