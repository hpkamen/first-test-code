# Gomoku (五子棋) Android App

## 项目介绍

这是一个完整的五子棋（Gomoku）Android应用，可以在安卓手机上运行。游戏支持黑白两色棋子，实现了完整的游戏逻辑和用户界面。

## 功能特性

- **完整的游戏逻辑**
  - 15x15的游戏棋盘
  - 黑白双方轮流下棋
  - 自动检测五子连线（胜利条件）
  - 支持四个方向的检测（水平、竖直、两条对角线）

- **用户界面**
  - 自定义绘制的棋盘视图
  - 实时显示当前玩家
  - 显示最后落子位置
  - 游戏结束弹窗提示

- **交互功能**
  - 点击棋盘放置棋子
  - 重新开始游戏
  - 撤销上一步

## 项目结构

```
app/src/main/
├── java/com/example/gomoku/
│   ├── MainActivity.kt              # 主活动
│   ├── game/
│   │   └── GameBoard.kt            # 游戏逻辑核心
│   └── ui/
│       └── GomokuBoardView.kt       # 棋盘自定义视图
├── res/
│   ├── layout/
│   │   └── activity_main.xml        # 主界面布局
│   ├── values/
│   │   ├── strings.xml             # 字符串资源
│   │   ├── colors.xml              # 颜色资源
│   │   └── themes.xml              # 主题资源
└── AndroidManifest.xml              # 清单文件
```

## 环境要求

- Android SDK 34 (目标SDK)
- Android 7.0 (API 24) 及以上
- Kotlin 1.9.0
- Gradle 8.1.0

## 安装和运行

### 使用Android Studio

1. 克隆项目：
   ```bash
   git clone https://github.com/hpkamen/first-test-code.git
   cd first-test-code
   ```

2. 在Android Studio中打开项目

3. 同步Gradle文件

4. 连接Android设备或启动模拟器

5. 点击"Run" > "Run 'app'"来运行应用

### 使用命令行

```bash
# 构建APK
./gradlew build

# 运行应用
./gradlew installDebug
```

## 游戏规则

1. 黑棋先行
2. 玩家轮流在空位放置棋子
3. 先形成五个连续棋子（水平、竖直或对角线）的玩家获胜
4. 支持撤销操作和重新开始游戏

## 核心代码说明

### GameBoard.kt
- `placeStone()`: 在指定位置放置棋子
- `checkWin()`: 检查是否形成五子连线
- `undoMove()`: 撤销上一步
- `reset()`: 重置游戏

### GomokuBoardView.kt
- 自定义View，负责棋盘的绘制
- 处理触摸事件，实现点击放置棋子
- 支持棋盘动画和UI更新

### MainActivity.kt
- 应用入口
- 管理游戏状态和UI更新
- 处理游戏结束弹窗

## 贡献

欢迎提交Issue和Pull Request！

## 许可证

MIT License
