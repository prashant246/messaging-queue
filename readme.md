#Messaging Queue System

Swagger Link - http://127.0.0.1:21000/swagger-ui.html

##QueueController
#####POST /v1/queue (createQueue)
``
This will create a queue.
``
#####GET /v1/queue/{queueName} (getQueue)
``
This will get basic details of the queue.
``
##SubscriptionController
#####POST /v1/subscribe (createSubscription)
``
This will create the subscription for the queue.
``
#####GET /v1/subscribe/{subscriptionName}/relay/start (startRelaying)
``
This will start the relaying of messages for subscriber.
``
#####GET /v1/subscribe/{subscriptionName}/relay/stop (stopRelaying)
``
This will stop the relaying of messages for subscriber.
``
#####POST /v1/subscribe/retry/{subscriptionName} (retrySubscription)
``
This will retry the sidelined messages for the subcriber
``
##PublisherController
#####POST /v1/publish (publishMessage)
``
This will publish the message to a queue``
##Scheduler
``
A scheduler has been use to relay messages to the subscriber. It will relay the message 5 times after that message will be sidelined and need to retry manually.``
