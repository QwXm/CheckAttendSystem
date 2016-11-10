/*如果需要测试签到流程，需要将每节课的时间修改，以匹配所测试学生所上课程节次*/
/*比如当前周为 周五 那么签到学生周五应该有课 且该门课的节次处于某个时间段里 以及该门课程在当前时刻并没有结课*/
/*插入管理员*/
INSERT INTO `administrator` VALUES ('1', '111', '111');
INSERT INTO `administrator` VALUES ('2', '222', '222');

/*插入教师*/
INSERT INTO `teacher` VALUES ('1', '黄丽娟', '123', 'teacher1', '001');
INSERT INTO `teacher` VALUES ('2', '张凤霞', '123', 'teacher2', '002');
INSERT INTO `teacher` VALUES ('3', '李圣杰', '123', 'teacher3', '003');

/*插入课程信息*/
/*---------------------------id---周几--结束周--名称-------节次--开始周-学期*/
INSERT INTO `course` VALUES ('1', '1,3,4,5','20','数据库原理', '1', '1','1');
INSERT INTO `course` VALUES ('2', '2,5','20','大学物理', '2', '1','1');
INSERT INTO `course` VALUES ('3', '1,3,4,5','20','数据结构', '3', '1','1');
INSERT INTO `course` VALUES ('4', '2,4','20','大学英语', '4', '1','1');
INSERT INTO `course` VALUES ('5', '3,4','20','软件工程', '5', '1','1');
/*以上课程周四周四不同节次都有，以方便测试*/

/*课程教师关联*/
/*------------------------------------教师ID--课程ID*/
INSERT INTO `teacher_course` VALUES ('1', '1');
INSERT INTO `teacher_course` VALUES ('1', '2');
INSERT INTO `teacher_course` VALUES ('1', '3');
INSERT INTO `teacher_course` VALUES ('2', '3');
INSERT INTO `teacher_course` VALUES ('2', '4');
INSERT INTO `teacher_course` VALUES ('2', '5');

/*添加学生*/
/*---------------------------------姓名-------学号---*/
INSERT INTO `student` VALUES ('1', '张三', '1307070301');
INSERT INTO `student` VALUES ('2', '李四', '1307070302');
INSERT INTO `student` VALUES ('3', '王五', '1307070303');
INSERT INTO `student` VALUES ('4', '赵六', '1307070304');
INSERT INTO `student` VALUES ('5', '霍霍', '1307070305');
INSERT INTO `student` VALUES ('6', '二帅', '1307070306');
INSERT INTO `student` VALUES ('7', '城建', '1307070307');
INSERT INTO `student` VALUES ('8', '霍老大', '1307070308');
INSERT INTO `student` VALUES ('9', '杨浩宇', '1307070309');
INSERT INTO `student` VALUES ('10', '黄精', '1307070310');

/*添加学生与教师之间的关系*/
/*------------------------------------教师ID--学生ID*/
INSERT INTO `student_teacher` VALUES ('1', '1');
INSERT INTO `student_teacher` VALUES ('1', '2');
INSERT INTO `student_teacher` VALUES ('1', '3');
INSERT INTO `student_teacher` VALUES ('1', '4');
INSERT INTO `student_teacher` VALUES ('1', '5');
INSERT INTO `student_teacher` VALUES ('2', '6');
INSERT INTO `student_teacher` VALUES ('2', '7');
INSERT INTO `student_teacher` VALUES ('2', '8');
INSERT INTO `student_teacher` VALUES ('2', '9');
INSERT INTO `student_teacher` VALUES ('2', '10');

/*添加学生与课程之间的关系*/
/*----------------------------------课程ID--学生ID*/
INSERT INTO `student_course` VALUES ('1', '1');
INSERT INTO `student_course` VALUES ('1', '2');
INSERT INTO `student_course` VALUES ('1', '3');
INSERT INTO `student_course` VALUES ('1', '4');
INSERT INTO `student_course` VALUES ('1', '5');
INSERT INTO `student_course` VALUES ('2', '1');
INSERT INTO `student_course` VALUES ('2', '2');
INSERT INTO `student_course` VALUES ('2', '3');
INSERT INTO `student_course` VALUES ('2', '4');
INSERT INTO `student_course` VALUES ('3', '5');
INSERT INTO `student_course` VALUES ('4', '6');
INSERT INTO `student_course` VALUES ('4', '7');
INSERT INTO `student_course` VALUES ('5', '8');
INSERT INTO `student_course` VALUES ('5', '9');
INSERT INTO `student_course` VALUES ('5', '10');

/*插入规则*/
/*-----------------------规则ID--*/
INSERT INTO `rule` VALUES ('1');

/*插入扣分规则具体内容*/
/*----------------------------规则ID-扣除分数值--迟到或者早退时间---listID*/
INSERT INTO `rules_info` VALUES ('1', '2', '5', '0');
INSERT INTO `rules_info` VALUES ('1', '3', '10', '1');
INSERT INTO `rules_info` VALUES ('1', '3', '20', '2');
INSERT INTO `rules_info` VALUES ('1', '2', '10', '3');

/*插入时间信息规则*/
/*---------------------------------id--开学时间-------使用本规则时间*/
INSERT INTO `timemanager` VALUES ('1', '2016-09-14', null);

/*时间规则具体信息*/
INSERT INTO `time_info` VALUES ('1', '09:00:00', '08:15:00', '0');
INSERT INTO `time_info` VALUES ('1', '09:50:00', '09:05:00', '1');
INSERT INTO `time_info` VALUES ('1', '21:55:00', '10:10:00', '2');
INSERT INTO `time_info` VALUES ('1', '11:45:00', '11:00:00', '3');
INSERT INTO `time_info` VALUES ('1', '23:10:46', '19:00:37', '4');
INSERT INTO `time_info` VALUES ('1', '11:12:14', '10:12:12', '5');
INSERT INTO `time_info` VALUES ('1', '14:12:50', '13:12:46', '6');
INSERT INTO `time_info` VALUES ('1', '15:13:03', '14:13:08', '7');

/*插入签到信息*/
/*--------------------------ID---签到日期-----扣除分数---扣分描述-----教师ID-课程ID--学生ID*/
INSERT INTO `sign` VALUES ('1', '2016-11-07', '-5', '严重迟到+早退', '1', '1', '1');