/* 创建数据库 */
CREATE database shixun;

/* 使用数据库 */
use shixun;

CREATE TABLE IF NOT EXISTS `t_user`(
   `user_id` INT UNSIGNED AUTO_INCREMENT comment '自增id',
   `username` VARCHAR(16) NOT NULL,
   `password` VARCHAR(32) NOT NULL,
   PRIMARY KEY ( `user_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* salt库理论上应该存在另一个数据库服务器，防止被同时拖库 */
CREATE TABLE IF NOT EXISTS `t_salt`(
   `salt_id` INT UNSIGNED AUTO_INCREMENT comment '自增id',
   `username` VARCHAR(16) NOT NULL,
   `salt` VARCHAR(32) NOT NULL,
   PRIMARY KEY ( `salt_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS `t_game`(
   `game_id` INT UNSIGNED AUTO_INCREMENT comment '自增id',
   `game_name` VARCHAR(64) NOT NULL comment '游戏名称',
   `release_date` DATE NOT NULL comment '发布日期，年月日',
   `game_price` SMALLINT NOT NULL comment '游戏价格',
   `game_publisher` VARCHAR(64) NOT NULL comment '发行商', 
   `game_developer` VARCHAR(64) NOT NULL comment '开发商', 
   `image_url` VARCHAR(256) NOT NULL comment '图片URL',
   `game_introduce` VARCHAR(2048) NOT NULL comment '游戏简介',
   `game_type` VARCHAR(16) NOT NULL comment '游戏种类',
   PRIMARY KEY ( `game_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 订单里的user和game是多对多，用一张表来保存，两个外键 */
CREATE TABLE IF NOT EXISTS `t_order` (
   `order_id` INT UNSIGNED AUTO_INCREMENT comment '自增id',
   `order_no`  VARCHAR(21) NOT NULL comment '订单编号',
   `user_id` INT UNSIGNED NOT NULL comment '订单用户',
   `game_id` INT UNSIGNED NOT NULL comment '订单游戏物品',
   PRIMARY KEY (`order_id`),
   FOREIGN KEY(`user_id`) REFERENCES `t_user`(`user_id`),
   FOREIGN KEY(`game_id`) REFERENCES `t_game`(`game_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 用户库游戏表,同样是多对多的关系 */
CREATE TABLE IF NOT EXISTS `t_user_store` (
   `order_id` INT UNSIGNED AUTO_INCREMENT comment '自增id',
   `user_id` INT UNSIGNED NOT NULL comment '用户',
   `game_id` INT UNSIGNED NOT NULL comment '游戏物品',
   PRIMARY KEY (`order_id`),
   FOREIGN KEY(`user_id`) REFERENCES `t_user`(`user_id`),
   FOREIGN KEY(`game_id`) REFERENCES `t_game`(`game_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 账户测试样例 ，原始密码是123456 */
INSERT INTO t_user VALUES (null, 123456, '7a6878a43caf213ec2893760eafe7248');
INSERT INTO t_salt VALUES (null, 123456, 'A02XjClHPvwEp9bpv4519QtxBXBRabhp');



/* 游戏入库，id默认为null即可, 记得是 YYYY-MM-DD ，缺零要补上 */
INSERT INTO t_game VALUES (null, '光之傳說', '2021-09-13', 48, '株式会社デスクワークス', '株式会社アニプレックス', 
   'https://cdn.cloudflare.steamstatic.com/steam/apps/1839510/header_schinese.jpg?t=1656472478',
   '冒險的時間到了！ 下課鐘聲響起，角色扮演遊戲時間正式上線！快點一同暢玩由超級熱愛遊戲的少年「健太」，在筆記本上親自描繪而出的角色扮演遊戲！',
   'role_play');

INSERT INTO t_game VALUES (null, 'Haven', '2020-12-03', 109, 'The Game Bakers', 'The Game Bakers', 
   'https://cdn.cloudflare.steamstatic.com/steam/apps/983970/header.jpg?t=1646316502',
   '为了能比翼双飞，一对恋人放弃一切，逃往一颗失落行星。在这款关乎爱、抗争与自由的角色扮演冒险游戏中，滑空飞越神秘之地，探索一个破碎的世界并挺身对抗企图拆散他们的敌人。',
   'role_play');

INSERT INTO t_game VALUES (null, 'ASSASSIN’S CREED® ORIGINS', '2017-10-27', 199, 'Ubisoft', 'Ubisoft Montreal', 
   'https://cdn.cloudflare.steamstatic.com/steam/apps/582160/header.jpg?t=1603213581',
   '《ASSASSIN’S CREED® ORIGINS》是一次全新的开始 *《Assassin’s Creed》Discovery Tour现已免费更新！* 王权和阴谋统治下的古埃及时代正慢慢消失在争权夺位的冷酷之战中。重返刺客兄弟会起源的最初时刻，揭开不为人知的秘密和被遗忘的神秘事件',
   'role_play');

INSERT INTO t_game VALUES (null, 'Zombie Exodus: Safe Haven', '2016-10-29', 268, 'Hosted Games', 'Hosted Games', 
   'https://cdn.cloudflare.steamstatic.com/steam/apps/543980/header.jpg?t=1644241035',
   '《Total War™: THREE KINGDOMS》首次在这一获奖无数的策略类游戏系列中重塑中国古代的烽火传奇。在扣人心弦的回合制战役中，可以建设国家，治国理政；在令人叹为观止的即时战斗中，则可以征战沙场，破军杀敌。《THREE KINGDOMS》将二者巧妙结合，重新定义了那段英雄辈出的传奇历史。',
   'role_play');


INSERT INTO t_game VALUES (null, 'Snake Pass', '2017-03-28', 119, 'Secret Mode', 'Sumo Digital', 
   'https://cdn.cloudflare.steamstatic.com/steam/apps/544330/header.jpg?t=1646218699',
   '蜿蜒爬行、节节攀升，在这款由Sumo Digital出品、玩法独树一帜的物理解谜平台冒险游戏中，登顶乐土之巅Haven Tor！',
   'role_play');

INSERT INTO t_game VALUES (null, 'DRAGON BALL Z: KAKAROT', '2020-01-17', 298, 'BANDAI NAMCO Entertainment', 'CyberConnect2 Co. Ltd', 
    'https://media.st.dl.eccdnx.com/steam/apps/851850/header.jpg?t=1653933519', 
    '《七龙珠Z 卡卡洛特》里可体验到悟空及其他Z战士的故事！除了史诗级的对战外，在七龙珠Z世界里亦可以与悟空、悟饭及贝吉达等等角色进行打斗、钓鱼、用餐及训练.',
    'cartoon');

INSERT INTO t_game VALUES (null, 'Muse Dash', '2019-06-20', 18, 'hasuhasu', 'peropero',
    'https://media.st.dl.eccdnx.com/steam/apps/774171/header_alt_assets_4.jpg?t=1655707604',
    '你喜欢可爱的美少女角色？还是节奏感十足的音乐？如果有一个地方可以同时满足这两个愿望……那就是狂拽跑酷和传统音游结合的世界 ★★★ —— 《 Muse Dash 》!!', 
    'cartoon');


INSERT INTO t_game VALUES (null, 'Project DIVA MEGA39’s', '2022-05-27', 239, 'SEGA', 'SEGA',
    'https://media.st.dl.eccdnx.com/steam/apps/1761390/header_schinese.jpg?t=1653923203',
    '简介：初音未来主演的节奏游戏最新作《初音未来 Project DIVA MEGA39s+》于Steam登场！ Project DIVA系列的集大成之作在升级之后重新回归。 本作收录了超过170首初音未来历史上的名曲！',
    'cartoon');

INSERT INTO t_game VALUES (null, 'Neon White', '2022-06-27', 138, 'Annapurna Interactive', 'Angel Matrix',
    'https://media.st.dl.eccdnx.com/steam/apps/1533420/header.jpg?t=1655938828',
    'Neon White 是一款快若闪电的第一人称动作游戏，你在游戏中的使命是消灭天堂里的恶魔。你将化身为来自地狱的刺客 White，与其他屠魔者争夺永居天堂的一线机会。其他刺客看起来都有点眼熟，但是...他们是否都是你前世所熟识的人？',
    'cartoon');

INSERT INTO t_game VALUES (null, 'DJMAX RESPECTV', '2020-03-12', 138, 'NEOWIZ', 'NEOWIZ', 
    'https://media.st.dl.eccdnx.com/steam/apps/960170/header.jpg?t=1647583288',
    '最佳人气音游《DJMAX》终于上线STEAM了！DJMAX系列的最新作品《DJMAX RESPECT》以进一步发展的内容推出在Steam。[广泛的音乐类型]不仅是Pop,Rock,Electronic类型,还有Abient,Jazz,Easy Listening！DJMAX可以欣赏更广泛的音乐。',
    'cartoon');


INSERT INTO t_game VALUES (null,
'Age of Empires IV',
'2021-10-28',
248,
'Relic Entertainment,World''s Edge',
'Xbox Game Studios',
 'https://media.st.dl.eccdnx.com/steam/apps/1466860/header.jpg?t=1656091390',
'最受欢迎的即时战略游戏之一将随《帝国时代 IV》荣耀回归，让您置身于塑造世界的史诗般历史战役的中心。本作同时采用常见及创新手法，让您能在以令人惊艳 4K 视觉保真度呈现的浩大场景中拓展帝国版图，并成功带领进化版即时战略游戏迈向新世代。', 
'strategy'
);

INSERT INTO t_game VALUES( 
null,
'Cities: Skylines',
'2015-03-11',
108,
'Colossal Order Ltd',
'Paradox Interactive',
'https://media.st.dl.eccdnx.com/steam/apps/255710/header.jpg?t=1654076112',
'《城市：天际线》是对经典城市模拟类游戏的现代演绎。该游戏引入了全新的游戏玩法元素，让玩家切身体会到创造和维持一座真正城市的兴奋和艰辛，同时扩展了城市建设体验中的一些经久不衰的主题。',
'strategy');
INSERT INTO t_game VALUES(null,
'Sid Meier''s Civilization VI',
'2016-10-21',
199,
'Firaxis Games, Aspyr (Mac), Aspyr (Linux)',
'2K, Aspyr (Mac), Aspyr (Linux)',
'https://media.st.dl.eccdnx.com/steam/apps/289070/header_schinese.jpg?t=1656624374',
'《文明VI》是屡获殊荣的《文明》系列的最新作品。扩张疆域，发展文化，并与历史上最伟大的领袖正面交锋。你的文明是否能经得起时间的考验？',
'strategy');

INSERT INTO t_game VALUES(null,'Total War: THREE KINGDOMS',
'2019-05-23',
268,
'CREATIVE ASSEMBLY, Feral Interactive (Mac), Feral Interactive ',
'SEGA, Feral Interactive (Mac), Feral Interactive (Linux)',
'https://media.st.dl.eccdnx.com/steam/apps/779340/header.jpg?t=1653315123',
'《Total War™: THREE KINGDOMS》首次在这一获奖无数的策略类游戏系列中重塑中国古代的烽火传奇。在扣人心弦的回合制战役中，可以建设国家，治国理政；在令人叹为观止的即时战斗中，则可以征战沙场，破军杀敌。《THREE KINGDOMS》将二者巧妙结合，重新定义了那段英雄辈出的传奇历史。', 
'strategy');

INSERT INTO t_game VALUES(null,'Stacklands',
'2022-04-09',
22,
'Sokpop Collective',
'Sokpop Collective',
'https://media.st.dl.eccdnx.com/steam/apps/1948280/header.jpg?t=1656054580',
'《Stacklands》是一款村庄建设游戏，你可以在那里堆叠卡牌以收集食物、修建建筑、并与各种生物战斗。 例如，将“村民”卡牌拖至“浆果灌木”顶部将会生成“浆果”卡牌，村民们可以吃这些浆果卡牌来生存！出好你的卡牌，扩建你的村庄！',
'strategy'
);


INSERT INTO t_game VALUES (null, 'RAFT', '2022-06-21', 101, 'Redbeet Interactive', 'Axolot Games', 
   'https://cdn.cloudflare.steamstatic.com/steam/apps/648800/header.jpg?t=1655744208',
   'Raft throws you and your friends into an epic oceanic adventure! Alone or together, players battle to survive a perilous voyage across a vast sea! Gather debris, scavenge reefs and build your own floating home, but be wary of the man-eating sharks!',
   'sandbox');

INSERT INTO t_game VALUES (null, 'RUST', '2018-02-09', 81, 'Facepunch Studios', 'Facepunch Studios', 
   'https://media.st.dl.eccdnx.com/steam/apps/252490/header_alt_assets_16.jpg?t=1656003795',
   'The only aim in Rust is to survive. Everything wants you to die - the island’s wildlife and other inhabitants, the environment, other survivors. Do whatever it takes to last another night.',
   'sandbox');

INSERT INTO t_game VALUES (null, 'Terraria', '2011-05-17', 18, ' Re-Logic', 'Re-Logic', 
   'https://media.st.dl.eccdnx.com/steam/apps/105600/header.jpg?t=1590092560',
   '挖掘，战斗，探索，建造！在这个动感十足的冒险游戏里没有什么是不可能的。四人包依然可用！',
   'sandbox');

INSERT INTO t_game VALUES (null, '英灵神殿', '2021-02-02', 49, ' Coffee Stain Publishing', ' Coffee Stain Publishing', 
   'https://media.st.dl.eccdnx.com/steam/apps/892970/header_schinese.jpg?t=1647939640',
   '游戏简介：你是一名战死的英灵战士，女武神们把你的灵魂摆渡到了英灵神殿，北境第十大神界。而你却被一些混沌之物和古老的众神之敌困在了这里，你是这个原始炼狱最新的守护者，而你的任务就是诛杀奥丁旧时的敌人，给英灵神殿带来应有的秩序。',
   'sandbox'); 

INSERT INTO t_game VALUES (null, 'The Forest', '2018-05-01', 18, 'Endnight Games Ltd', 'Endnight Games Ltd', 
   'https://media.st.dl.eccdnx.com/steam/apps/242760/header.jpg?t=1590522045',
   'As the lone survivor of a passenger jet crash, you find yourself in a mysterious forest battling to stay alive against a society of cannibalistic mutants.',
   'sandbox');

INSERT INTO t_game VALUES(null,'Overcooked! All You Can Eat','2021-03-24','137.5','Team17 Digital, Ghost Town Games','Team17 Digital','https://cdn.akamai.steamstatic.com/steam/apps/1243830/header.jpg?t=1652268022','盡情體驗洋蔥王國的一切。徹底重製、重新烹調。', 'relaxation');
INSERT INTO t_game VALUES(null,'AMONG US','2018-11-17','22.4','Innersloth','Innersloth','https://cdn.akamai.steamstatic.com/steam/apps/945360/header.jpg?t=1646296970','一款線上與單機皆可玩的派對遊戲，4 至 15 名玩家相互合作與背叛... 一切都發生在太空之中！', 'relaxation');
INSERT INTO t_game VALUES(null,'The Stanley Parable: Ultra Deluxe','2022-04-28','73.03','Crows Crows Crows','Crows Crows Crows','https://media.st.dl.eccdnx.com/steam/apps/1703340/header.jpg?t=1654715052','You will play as Stanley, and you will not play as Stanley. You will make a choice, and you will become powerless. ', 'relaxation');
INSERT INTO t_game VALUES(null,'The Sims™ 4','2014-09-02','34.75','Maxis','Electronic Arts','https://cdn.akamai.steamstatic.com/steam/apps/1222670/header.jpg?t=1656601504','享受在虛擬世界裡創造及控制人們的無上權力。 感受恣意控制的力量與自由、愉快玩樂，遊戲人生！', 'relaxation');
INSERT INTO t_game VALUES(null,'Stardew Valley','2016-02-27','59.4','ConcernedApe','ConcernedApe','https://cdn.akamai.steamstatic.com/steam/apps/413150/header.jpg?t=1608624324','Armed with hand-me-down tools and a few coins, you set out to begin your new life.', 'relaxation');