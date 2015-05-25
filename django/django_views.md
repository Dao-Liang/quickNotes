## Django Views ##


### django.http ###


### django.shortcuts ###




## Django Urls ##


### django.conf.url.url() ###
This method has four arguments:
    1. regex: specify a string pattern to match request url, url patterns

    2. view: a specified view function with an HttpRequest object as the 1st argument and any "captured" values from the regular expression as other arguments:
        positional arguments: when regex using simple capture
        keyword arguments: when regex using named captures

    3. kwargs: any keyword arguments cna be passed in a dictionary to the target view.

    4. name: Name this "Url" and lets us refer to it from any place, especially templates. this make the global changes to the url patterns of project only touching a single file

