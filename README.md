demo-thread
===========

Just a demo that shows, in a thread pool with 2 threads, when two runnables are submitted, they occupy the slot so the
thread submitted job cannot be executed.

The thread-in-runtime.png shows how the thread run in runtime.
 cyan color - sleep time
 green color - running dummy process that eat CPU
 yellow color - runnable has finished and the thread becomes idle
