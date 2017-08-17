package com.wolves.zerotoone.orm.transaction;

import java.sql.Connection;

/** 
* @ClassName: MyTransactionIsolationLevel 
* @Description: 事务是DBMS得执行单位,事务是必须满足4个条件（ACID）:
* 	原子性(Automic): 事务在执行性，要做到“要么不做，要么全做”
* 	一致性(Consistency):事务操作之后，数据库所处的状态与业务规则一致
* 	隔离性(Isolation):多个事务并发执行，应该像各个事务独立执行一样
* 	持久性(Durability):事务提交后，被持久化到数据库
* 关于更新丢失，脏读，不可重复读，幻读
* 	更新丢失:当两个或多个事务选择同一行，然后基于最初选定的值更新该行时，由于每个事务都不知道其他事务的存在，就会发生丢失更新问题.
* 	脏读:一个事物读取了另一个未提交的并行事务写的数据
* 	不可重复读:一个事物重新读取之前读取的数据，发现该数据被另一个已提交的事务修改过
* 	幻读:一个事物重新执行一个查询，返回一套符合查询条件的行，发现这些行因为其他最近的事务而发生了改变
* 	
* 	
* 事务隔离级别描述：
* 	READ UNCOMMITTED：幻读，不可重复读和脏读均允许；
* 	READ COMMITTED：允许幻读和不可重复读，但不允许脏读；
*   REPEATABLE READ：允许幻读，但不允许不可重复读和脏读；
* 	SERIALIZABLE:幻读，不可重复读和脏读都不允许；
* 		ORACLE默认的是 READ COMMITTED。
*    	MYSQL默认的是 REPEATABLE READ。 
* @author wangfei.hu@pactera.com 
* @date 2017年8月17日 下午5:02:55 
*  
*/
public enum MyTransactionIsolationLevel {
	NONE(Connection.TRANSACTION_NONE), 
	READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED), 
	READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED), 
	REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ), 
	SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);
	private final int level;

	private MyTransactionIsolationLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

}
