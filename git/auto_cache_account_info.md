<pre><code>
$git config --global credential.helper cache
# Set git to use the credential memory cache

$git config --global credential.helper 'cache --timeout=3600'
# Set the cache to timeout after 1 hour (setting is in seconds)
</pre></code>
