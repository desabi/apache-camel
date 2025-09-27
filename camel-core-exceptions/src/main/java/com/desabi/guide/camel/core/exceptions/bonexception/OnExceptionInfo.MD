
Using onException Clause:

You can define onException blocks to catch specific exceptions (or groups of exceptions) and then define how to handle them (like logging, redelivery, processing in a different way, etc.).

It supports features such as:

Redelivery: Configure redelivery policies (number of attempts, delays, backoff multipliers, etc.) before giving up.

Handled: Mark the exception as handled so that the route consumer does not see it as an exception (the exchange will continue as normal, but you can set a response body or header).

Continued: Similar to handled but the exception is cleared and the exchange continues to be routed from the point of failure.

Custom Processor: You can use a processor to handle the exception (e.g., set a fault message, log, etc.).

Dead Letter Channel: Define a dead letter queue to send the message when all redelivery attempts fail.

