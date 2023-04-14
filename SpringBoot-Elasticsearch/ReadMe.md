#
Spring Data ElasticSearch有下边这几种方法操作ElasticSearch：

* `ElasticsearchRepository`（传统的方法，可以使用）
* `ElasticsearchRestTemplate`（推荐使用，基于RestHighLevelClient）
* `RestHighLevelClient`（推荐度低于ElasticsearchRestTemplate，因为API不够高级）
 