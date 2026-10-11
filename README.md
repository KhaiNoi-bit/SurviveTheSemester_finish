[README.md](https://github.com/user-attachments/files/32794952/README.md)
# 🎓 Survive the Semester

เกมจำลองชีวิตนักศึกษา 1 ภาคเรียน (8 สัปดาห์) สำหรับ Android เขียนด้วย **Java + XML layout** โครงสร้างแบบ **OOP + MVC**

> เป้าหมาย: บริหาร 5 ค่าสถานะ (การเรียน, พลังงาน, ความสุข, ความเครียด, เงิน) ให้ผ่านสอบปลายภาคทุกวิชา แล้วไปให้ถึง **Ending A** ให้ได้

---

## สารบัญ

1. [วิธีเล่น](#1-วิธีเล่น)
2. [โครงสร้างโปรเจกต์](#2-โครงสร้างโปรเจกต์)
3. [ภาพรวมสถาปัตยกรรม MVC](#3-ภาพรวมสถาปัตยกรรม-mvc)
4. [Model](#4-model)
5. [Controller](#5-controller)
6. [View (Activity)](#6-view-activity)
7. [Layout และ UI (res/)](#7-layout-และ-ui-res)
8. [ลำดับการทำงานของเกม](#8-ลำดับการทำงานของเกม)
9. [ระบบเซฟ/โหลด](#9-ระบบเซฟโหลด)
10. [ตารางค่าเกม (Balance)](#10-ตารางค่าเกม-balance)
11. [วิธีเพิ่มเนื้อหาใหม่](#11-วิธีเพิ่มเนื้อหาใหม่)
12. [Build & Run](#12-build--run)

---

## 1. วิธีเล่น

- ผู้เล่นเป็นนักศึกษา เริ่มที่สัปดาห์ 1 วันจันทร์ ไปจนถึงสอบปลายภาค (สัปดาห์ที่ 8 วันศุกร์)
- **1 วันมี 3 Actions** เลือกกิจกรรมได้ 3 ครั้ง ครบแล้วขึ้นวันใหม่ (จันทร์–ศุกร์ ครบ 5 วัน = 1 สัปดาห์)
- ระหว่างเล่นจะมี **Event สุ่ม** และ **Assignment** ให้ตัดสินใจ
- **ความพร้อมสอบรายวิชา** จะลดลงทุกสัปดาห์ (ลืมเนื้อหา) ต้องเข้าเรียน/อ่านหนังสือเพื่อเพิ่ม
- เมื่อครบ 8 สัปดาห์จะเข้าสู่ **Final Exam** แล้วได้ **Ending A–E**

### ค่าสถานะ 5 ค่า

| ค่า | ความหมาย | ช่วง |
|---|---|---|
| 📚 การเรียน (study) | เพิ่มจากเข้าเรียน/อ่านหนังสือ | 0–100 |
| ⚡ พลังงาน (energy) | ลดจากกิจกรรม เพิ่มจากการนอน | 0–100 |
| 😊 ความสุข (happy) | เพิ่มจากกิจกรรมที่ชอบ | 0–100 |
| 😰 ความเครียด (stress) | เพิ่มจากเรียน/ทำงาน ลดจากนอน/เล่นเกม | 0–100 |
| 💰 เงิน (money) | ได้จากทำงานพิเศษ ใช้จ่ายในร้านค้า/Event | 0 ขึ้นไป (ไม่มีเพดาน) |

### สถานะวิกฤต

| เงื่อนไข | ผล |
|---|---|
| พลังงาน = 0 | กิจกรรมที่ใช้แรง (เข้าเรียน/อ่านหนังสือ/ทำงาน) ถูกล็อก ต้องไปนอน |
| ความเครียด = 100 (Stress Overload) | ค่าการเรียนและความพร้อมวิชาที่ได้รับ **ลดลงครึ่งหนึ่ง** และโดนหักคะแนนสอบ 10 |
| เงิน = 0 | กิจกรรมที่ต้องเสียเงินทำไม่ได้ |
| การเรียน < 30 | ขึ้นคำเตือนเสี่ยงสอบไม่ผ่าน |

---

## 2. โครงสร้างโปรเจกต์

```
app/src/main/
├── AndroidManifest.xml
├── java/com/example/survivethesemester/
│   ├── model/                    ← ข้อมูลและกฎพื้นฐานของเกม
│   │   ├── Player.java
│   │   ├── Semester.java
│   │   ├── Subject.java
│   │   ├── Action.java
│   │   ├── Event.java
│   │   ├── Item.java
│   │   └── Exam.java
│   ├── controller/               ← ตรรกะเกม (ไม่ยุ่งกับหน้าจอ)
│   │   ├── GameController.java   (Singleton ตัวกลาง)
│   │   ├── PlayerController.java
│   │   ├── ActivityController.java
│   │   ├── EventController.java
│   │   ├── ExamController.java
│   │   ├── ShopController.java
│   │   └── SaveController.java
│   └── view/                     ← หน้าจอ (Activity) และตัวช่วย UI
│       ├── Ui.java
│       ├── MainActivity.java
│       ├── CreateCharacterActivity.java
│       ├── GameActivity.java
│       ├── ActivityActivity.java
│       ├── StatusActivity.java
│       ├── EventActivity.java
│       ├── ExamActivity.java
│       ├── ResultActivity.java
│       ├── ShopActivity.java
│       └── SaveLoadActivity.java
└── res/
    ├── layout/                   ← หน้าจอทั้งหมด (XML)
    ├── drawable/                 ← พื้นหลัง ปุ่ม การ์ด (shape / layer-list / vector)
    └── values/                   ← colors_game.xml, styles_game.xml
```

---

## 3. ภาพรวมสถาปัตยกรรม MVC

```
   ┌────────────┐    เรียกใช้     ┌───────────────────┐    อ่าน/แก้ไข    ┌──────────┐
   │   View     │ ─────────────▶ │    Controller     │ ───────────────▶ │  Model   │
   │ (Activity) │ ◀───────────── │  GameController   │ ◀─────────────── │ Player…  │
   └────────────┘   อ่านผล/แสดง   └───────────────────┘                  └──────────┘
```

- **Model** เก็บข้อมูลและกฎง่ายๆ ของตัวเอง เช่น `Player.apply()` คุมค่าไม่ให้เกินช่วง
- **Controller** ตัดสินใจว่าเกมควรทำอะไร เช่น คำนวณผลกิจกรรม สุ่ม Event ตัดสินผลสอบ
- **View** แสดงผลและรับการกด ไม่มีการคำนวณกฎเกมเอง (เรียกผ่าน `GameController.get()`)

`GameController` เป็น **Singleton** เก็บสถานะเกมทั้งหมดไว้ที่เดียว ทุก Activity จึงเข้าถึงข้อมูลชุดเดียวกันได้ โดยไม่ต้องส่งผ่าน Intent

---

## 4. Model

### `Player`
ข้อมูลผู้เล่น

- ฟิลด์: `name`, `male`, `face` (0–3), และค่าสถานะ `study`, `energy`, `happy`, `stress`, `money`
- ค่าเริ่มต้น: study 65, energy 70, happy 60, stress 35, money 800
- `apply(dStudy, dEnergy, dHappy, dStress, dMoney)` เพิ่ม/ลดค่าทั้ง 5 พร้อมบีบให้อยู่ในช่วง 0–100 (เงินไม่ต่ำกว่า 0)
- `isExhausted()` (energy ≤ 0), `isStressOverload()` (stress ≥ 100), `isBroke()` (money ≤ 0)
- `faceEmoji(male, face)` คืน emoji ตัวละครตามเพศและหน้าตา

### `Semester`
ตัวนับเวลาของเทอม

- ค่าคงที่: `TOTAL_WEEKS = 8`, `DAYS_PER_WEEK = 5`, `MAX_ACTIONS = 3`, `WEEKLY_FORGET = 3`
- ฟิลด์: `week`, `day`, `actionsLeft`, และ `subjects` (รายวิชาทั้งหมด)
- `useAction()` ใช้ 1 action ถ้าหมดจะขึ้นวันใหม่ คืน `true` เมื่อขึ้นวันใหม่
- `nextDay()` (private) ขึ้นวัน ถ้าเกินวันศุกร์จะขึ้นสัปดาห์ใหม่ และ **ทุกวิชาลดลง 3** (ลืมเนื้อหา)
- `isOver()` เมื่อ `week > 8`
- `dayName()` ชื่อวันภาษาไทย, `daysLeft()` จำนวนวันที่เหลือถึงสอบ

### `Subject`
รายวิชา มี `name`, `emoji`, `readiness` (ความพร้อมสอบ 0–100)

- `add(d)` เพิ่ม/ลดค่าแบบจำกัดช่วง
- `defaults()` วิชาเริ่มต้น 5 วิชา: Programming 72, Database 65, Mathematics 58, English 81, Computer Network 70

### `Action`
กิจกรรมประจำวัน (ตั้งชื่อ `Action` เพื่อไม่ให้ชนกับ `android.app.Activity`)

- ผลกระทบต่อ 5 ค่า: `study, energy, happy, stress, money`
- `studyType` = ต้องเลือกวิชาก่อนทำ, `subjectGain` = ความพร้อมวิชาที่เพิ่ม
- `needsEnergy` = ถ้าพลังงานหมดจะถูกล็อก
- `defaults()` รายการกิจกรรมทั้งหมด (ดูตารางในหัวข้อ 10)

### `Event`
เหตุการณ์ที่ให้ผู้เล่นเลือก

- คลาสย่อย `Option` (ตัวเลือก) มี `label`, `hint`, ผลต่อ 5 ค่า และตัวช่วยแบบ fluent:
  - `.subj(d)` ผลต่อความพร้อมวิชาของ Event
  - `.risk(chance, failStudy, failSubject)` โอกาสพลาด (%) พร้อมโทษ
  - `.cost(n)` เสีย n Action
- Event สุ่ม 3 แบบ: `friend()` เพื่อนชวนกินข้าว, `rain()` ฝนตกหนัก, `computerBroken()` คอมเสีย
- `assignment(subjectIndex, subjectName)` Assignment ของวิชาที่สุ่มได้
- `random(Random r)` เลือก Event สุ่มหนึ่งใน 3 แบบ

### `Item`
สินค้าในร้าน มี `emoji, name, price` และผลต่อ `study, energy, happy` (`defaults()` ดูหัวข้อ 10)

### `Exam`
ผลสอบหนึ่งวิชา มี `emoji, subject, score` และ `passed()` เมื่อคะแนน ≥ `PASS_SCORE` (50)

---

## 5. Controller

### `GameController` (Singleton)
ตัวกลางเก็บสถานะเกมและประสานงาน controller อื่นทั้งหมด

| สมาชิก | หน้าที่ |
|---|---|
| `get()` | ขอ instance เดียวของเกม |
| `player`, `semester` | ข้อมูลเกมปัจจุบัน |
| `pending` | Event ที่รอผู้เล่นตัดสินใจ (`null` = ไม่มี) |
| `exams`, `ending` | ผลสอบและ Ending (`exams == null` = ยังไม่สอบ) |
| `log` | บันทึกเหตุการณ์ล่าสุด สูงสุด 20 รายการ |
| `newGame(name, male, face)` | เริ่มเกมใหม่ |
| `rebuild()` | สร้าง controller ย่อยใหม่ (หลังเริ่มเกม/โหลดเซฟ) |
| `hasGame()` | มีเกมที่กำลังเล่นอยู่หรือไม่ |
| `doAction(a, sub)` | ทำกิจกรรม บันทึก log ใช้ action แล้วสุ่ม Event |
| `resolveEvent(option)` | ใช้ผลตัวเลือกของ Event หัก action ถ้ามี |
| `ensureExam()` | คำนวณผลสอบและ Ending ครั้งเดียว |

**กฎการสุ่ม Event หลังทำกิจกรรม** (เมื่อเทอมยังไม่จบ)

1. ถ้าขึ้นวันใหม่ → โอกาส **30%** เกิด Assignment
2. ถ้าไม่เข้าเงื่อนไขข้อ 1 → โอกาส **35%** เกิด Event สุ่ม

### `PlayerController`
- `statusMessage()` ข้อความในกล่องคำพูดของตัวละคร (เปลี่ยนตามสภาพ: หมดแรง / เครียด / ไม่มีเงิน / ง่วง / ปกติ)
- `warnings()` รายการข้อความเตือนสถานะวิกฤตที่แสดงเป็นการ์ดแดง

### `ActivityController`
- `blockReason(action)` คืนเหตุผลที่ล็อกกิจกรรม (`null` = ทำได้) เช่น หมดแรง เงินไม่พอ
- `perform(action, subject)` ใช้ผลกิจกรรม ถ้า Stress Overload ผลบวกด้านการเรียน/วิชาจะลดครึ่งหนึ่ง

### `EventController`
- `chance(percent)` สุ่มผ่าน/ไม่ผ่านตามเปอร์เซ็นต์
- `randomEvent()`, `assignmentEvent(semester)` สร้าง Event
- `resolve(event, option, player, semester)` ใช้ผลตัวเลือก และถ้าตัวเลือกมี `failChance` จะสุ่มพลาด (เช่น "ลืมทำ Assignment") แล้วหักโทษเพิ่ม

### `ExamController`
- `takeExam(player, semester)` คำนวณคะแนนแต่ละวิชา

  ```
  คะแนน = ความพร้อมวิชา × 0.8 + ค่าการเรียน × 0.2 + สุ่ม(−5..+5)
          − 10 ถ้า Stress Overload   (หรือ − 5 ถ้าเครียด ≥ 80)
          − 5  ถ้าพลังงานหมด
  ```
  แล้วบีบให้อยู่ช่วง 0–100
- `average(exams)`, `allPassed(exams)`
- `decideEnding(exams, player)` ตัดสิน Ending ตามลำดับนี้

| ลำดับ | เงื่อนไข | Ending |
|---|---|---|
| 1 | สอบตกอย่างน้อย 1 วิชา | **E** Academic Disaster |
| 2 | เฉลี่ย ≥ 70 และ happy ≥ 60 และ money ≥ 1000 และ stress ≤ 40 | **A** นักศึกษาตัวอย่าง |
| 3 | happy ≥ 80 และเฉลี่ย < 70 | **D** เกมเมอร์ |
| 4 | money ≥ 3000 | **C** Working Student |
| 5 | นอกนั้น | **B** เรียนจบแบบเฉียดฉิว |

### `ShopController`
- `items` รายการสินค้า
- `buy(item)` ตรวจเงิน หักเงิน และเพิ่มค่าสถานะตามสินค้า คืน `false` ถ้าเงินไม่พอ

### `SaveController`
เก็บเซฟใน `SharedPreferences` (ดูหัวข้อ 9) มี `save`, `load`, `info`, `delete`, `deleteAll` และ `SLOTS = 3`

---

## 6. View (Activity)

| Activity | หน้าจอ | Layout | หน้าที่ |
|---|---|---|---|
| `MainActivity` | 1. เมนูหลัก | `activity_main` | เริ่มเกมใหม่ / โหลดเกม / วิธีเล่น (Dialog) / ตั้งค่า (ลบเซฟทั้งหมด) |
| `CreateCharacterActivity` | 2. สร้างตัวละคร | `activity_create_character` | ใส่ชื่อ เลือกเพศ เลือกหน้าตา แล้ว `newGame()` |
| `GameActivity` | 3. หน้าหลักในเกม | `activity_game` | แสดงวัน/ตัวละคร/สถานะ/คำเตือน และเมนูล่าง 5 ปุ่ม |
| `ActivityActivity` | 4. เลือกกิจกรรม | `activity_choose` + `item_action` | แสดงกิจกรรม ล็อกถ้าทำไม่ได้ เลือกวิชาถ้าเป็นกิจกรรมเรียน |
| `EventActivity` | 5. เหตุการณ์พิเศษ | `activity_event` + `item_option` | แสดง Event และตัวเลือก (กดย้อนกลับไม่ได้จนกว่าจะเลือก) |
| `ExamActivity` | 6. สอบปลายภาค | `activity_exam` + `item_exam` | แสดงคะแนนแต่ละวิชา |
| `ResultActivity` | 7/10. Ending A–E | `activity_result` | แสดง Ending สรุปสถานะ และกลับเมนู |
| `ShopActivity` | 8. ร้านค้า | `activity_shop` + `item_action` | ซื้อสินค้า (เงินไม่พอจะจางลง) |
| `SaveLoadActivity` | 9. บันทึก/โหลด | `activity_save_load` + `item_slot` | เลือกช่อง 1–3 แล้วบันทึกหรือโหลด (โหมดเข้าจากปุ่ม `mode` = save/load) |
| `StatusActivity` | สถานะ | `activity_status` | สถานะผู้เล่นและความพร้อมสอบรายวิชา |

### `Ui` (ตัวช่วยฝั่ง View)

| เมธอด | หน้าที่ |
|---|---|
| `dp`, `color`, `toast` | ตัวช่วยพื้นฐาน |
| `insets(view)` | กันเนื้อหาไม่ให้ชนแถบสถานะ/แถบนำทาง |
| `header(activity, title, back)` | ตั้งค่าหัวหน้าจอและปุ่มย้อนกลับ |
| `headerRight(activity, text)` | แสดงป้ายเหรียญ 🪙 มุมขวาของ header |
| `bigFirstLine(textView)` | ขยายไอคอน emoji บรรทัดแรกของปุ่มเมนูล่าง |
| `bindStat(row, label, value, max, colorRes, valueText)` | ใส่ข้อมูลลงแถวแถบสถานะ (`item_stat`) |
| `effects(...)` | สร้างข้อความผลกระทบ เช่น `📚+10 ⚡-20` (เขียว = ดี, แดง = ไม่ดี โดย 😰 กลับด้าน) |

### พฤติกรรมสำคัญของ `GameActivity.onResume()`
เรียงลำดับการตรวจสอบทุกครั้งที่กลับมาหน้านี้:

1. ถ้าไม่มีเกม (process ถูกปิด) → กลับเมนู
2. ถ้าเทอมจบ → ไป `ExamActivity`
3. ถ้ามี Event รออยู่ (`pending`) → ไป `EventActivity`
4. ไม่เช่นนั้นวาดหน้าจอตามปกติ

จึงไม่ต้องสั่งเปิด Event/สอบเองจากหน้าอื่น แค่ `finish()` แล้วให้หน้านี้ตัดสินต่อ

---

## 7. Layout และ UI (res/)

### Layout
- ทุกหน้า (ยกเว้นเมนูหลัก) ใช้โครงเดียวกัน: `layout_header` (`<include>`) + `ScrollView` พื้นหลัง `bg_scroll`
- ทุกหน้ามี View ราก id `root` ซึ่งใช้กับ `Ui.insets()`
- รายการ (กิจกรรม สินค้า ช่องเซฟ ผลสอบ) สร้างจากไฟล์ `item_*.xml` แล้ว inflate เข้า container ตอนรันไทม์

| ไฟล์ | ใช้ทำอะไร |
|---|---|
| `layout_header.xml` | หัวหน้าจอ: ปุ่มย้อน, ชื่อหน้า, ป้ายเหรียญ |
| `item_stat.xml` | แถวแถบสถานะ: ชื่อ + ProgressBar + ค่า |
| `item_action.xml` | การ์ดกิจกรรม/สินค้า: ไอคอน ชื่อ ผลกระทบ ราคา |
| `item_option.xml` | ปุ่มตัวเลือกของ Event |
| `item_exam.xml` | แถวผลสอบ |
| `item_slot.xml` | ช่องเซฟ |
| `item_warning.xml` | การ์ดคำเตือนสถานะวิกฤต |

### Drawable (`res/drawable/`)

| กลุ่ม | ไฟล์ | รูปแบบ |
|---|---|---|
| ปุ่ม 3D | `bg_btn_yellow/blue/teal/purple/green/red` | layer-list: ชั้นล่างสีเข้ม + ชั้นบนไล่เฉด |
| การ์ด | `bg_card`, `bg_card_selected`, `bg_card_warn`, `bg_card_scene`, `bg_nav` | layer-list ทำเงาบางด้านล่าง |
| ชิป/อินพุต | `bg_chip`, `bg_chip_selected`, `bg_input` | shape |
| ป้าย | `bg_pill`, `bg_coin`, `bg_badge`, `bg_bubble` | shape โค้งมน |
| ไอคอน/ตัวละคร | `bg_icon_tile`, `bg_avatar` | สี่เหลี่ยมโค้ง / วงกลมไล่เฉด |
| พื้นหลังหน้า | `bg_header`, `bg_scroll`, `bg_event`, `bg_menu` | gradient |
| แถบสถานะ | `bg_progress` | layer-list ที่มี `background` และ `progress` (สีถูกเปลี่ยนจากโค้งด้วย `setProgressTintList`) |
| ฉากเมนู | `ic_campus` | vector drawable รูปมหาวิทยาลัย |

### Values
- `colors_game.xml` พาเลตสีทั้งหมด (ชื่อ `blue`, `green`, `red`, `yellow`, `orange`, `purple`, `navy`, `gray`, `text_main` … ถูกอ้างจากโค้ด Java ด้วย จึงไม่ควรเปลี่ยนชื่อ)
- `styles_game.xml` สไตล์ `BigButton`, `Chip`, `NavButton`, `FieldLabel`, `CardTitle`

---

## 8. ลำดับการทำงานของเกม

```
เมนูหลัก ──เริ่มเกมใหม่──▶ สร้างตัวละคร ──ยืนยัน──▶ หน้าหลักในเกม
                                                      │
        ┌───────────────── เมนูล่าง ─────────────────┤
        │  กิจกรรม  สถานะ  Event(log)  Shop  เซฟ      │
        ▼                                             │
   เลือกกิจกรรม ── doAction() ──▶ useAction()         │
        │                          │                  │
        │                  ขึ้นวันใหม่? ─ สุ่ม Assignment 30%
        │                          └─ ไม่ใช่ ─ สุ่ม Event 35%
        └──────────── finish() ────────────────────────┘
                                   │ onResume ตรวจ
                    ┌──────────────┼───────────────┐
              มี pending?      เทอมจบ?           ปกติ
                    ▼               ▼                ▼
              EventActivity   ExamActivity     วาดหน้าจอ
                    │               ▼
                    │        ResultActivity (Ending A–E)
                    └─ เลือกตัวเลือก ─▶ resolveEvent() ─▶ กลับหน้าหลัก
```

---

## 9. ระบบเซฟ/โหลด

- เก็บใน `SharedPreferences` ชื่อ `survive_saves` คีย์ `slot1`–`slot3` เป็นสตริง JSON
- ข้อมูลที่เซฟ: เวลาเซฟ, ชื่อ/เพศ/หน้าตา, ค่าสถานะทั้ง 5, สัปดาห์/วัน/actions ที่เหลือ, ความพร้อมของแต่ละวิชา (ตามลำดับ)
- **ไม่ได้เซฟ:** Event ที่ค้างอยู่ (`pending`), ผลสอบ, บันทึกเหตุการณ์ (`log`) ถูกรีเซ็ตเมื่อโหลด
- `SaveController.info()` คืน `{เวลา, "Week n Day n", ชื่อ}` ใช้แสดงในรายการช่อง (`null` = ช่องว่าง)
- การเพิ่มวิชาใหม่ในภายหลังยังโหลดเซฟเก่าได้ (โค้ดอ่านตามจำนวนที่มี) แต่ลำดับวิชาต้องไม่สลับ

---

## 10. ตารางค่าเกม (Balance)

### กิจกรรม (`Action.defaults()`)

| กิจกรรม | 📚 | ⚡ | 😊 | 😰 | 💰 | เลือกวิชา | +วิชา | ใช้แรง |
|---|---|---|---|---|---|---|---|---|
| 🏫 เข้าเรียน | +10 | −15 | 0 | +5 | 0 | ใช่ | +8 | ใช่ |
| 📖 อ่านหนังสือ | +15 | −20 | −5 | +5 | 0 | ใช่ | +10 | ใช่ |
| 💼 ทำงานพิเศษ | 0 | −20 | 0 | +10 | +300 | ไม่ | – | ใช่ |
| 🎮 เล่นเกม | −5 | −10 | +20 | −10 | 0 | ไม่ | – | ไม่ |
| 😴 นอน | −5 | +30 | 0 | −15 | 0 | ไม่ | – | ไม่ |

### สินค้า (`Item.defaults()`)

| สินค้า | ราคา | ผล |
|---|---|---|
| ☕ กาแฟ | 30 | ⚡ +10 |
| 🍜 อาหาร | 50 | ⚡ +20 |
| 🎧 หูฟัง | 500 | 😊 +10 |
| 📚 หนังสือ | 200 | 📚 +10 |
| ⏰ นาฬิกา | 800 | ⚡ +15 |

### Event

| Event | ตัวเลือก → ผล |
|---|---|
| 🍜 เพื่อนชวนไปกินข้าว | ไป: 😊 +20, 💰 −150 · ไม่ไป: 📚 +5 |
| 🌧️ ฝนตกหนัก | นั่งรอฝนซา: เสีย 1 Action · เดินไปเรียน: ⚡ −10 · โดดเรียน: 📚 −10 |
| 💻 คอมพิวเตอร์เสีย | ซ่อม: 💰 −200 · ยืมเพื่อน: 😊 −5 · ไม่ทำ: 📚 −15 |
| 🚨 Assignment | ทำตอนนี้: 📚 +10, ⚡ −20, วิชา +10 · ทำพรุ่งนี้: 50% ลืม (📚 −8, วิชา −8) · ไม่ทำ: 📚 −5, วิชา −12 |

---

## 11. วิธีเพิ่มเนื้อหาใหม่

- **เพิ่มกิจกรรม:** เพิ่มบรรทัด `l.add(new Action(...))` ใน `Action.defaults()` UI จะสร้างการ์ดให้เอง
- **เพิ่มสินค้า:** เพิ่ม `l.add(new Item(...))` ใน `Item.defaults()`
- **เพิ่มวิชา:** เพิ่ม `l.add(new Subject(...))` ใน `Subject.defaults()` (ผลสอบ/หน้าสถานะจะรองรับอัตโนมัติ)
- **เพิ่ม Event สุ่ม:** เขียน factory method ใน `Event` (เหมือน `rain()`) แล้วเพิ่ม `case` ใน `Event.random()` และเปลี่ยนตัวเลข `r.nextInt(3)` ให้ตรงจำนวน
- **ปรับความยาก:** แก้ค่าคงที่ใน `Semester` (จำนวนสัปดาห์ Action ต่อวัน อัตราลืม), สูตรคะแนนใน `ExamController`, เกณฑ์ Ending ใน `decideEnding()`
- **ปรับหน้าตา:** แก้สีใน `colors_game.xml` หรือพื้นหลังใน `res/drawable/` ไม่ต้องแตะโค้ด Java
- **เปลี่ยน emoji เป็นรูปภาพ:** เปลี่ยน `TextView` ไอคอนใน `item_action.xml` (`tvIcon`) เป็น `ImageView` แล้วปรับจุดที่เรียก `setText(...emoji)` ใน Activity

---

## 12. Build & Run

1. เปิดโฟลเดอร์โปรเจกต์ด้วย **Android Studio**
2. รอ Gradle Sync ให้เสร็จ
3. เลือกอุปกรณ์หรือ Emulator แล้วกด **Run ▶**

หมายเหตุ

- แอปล็อกแนวตั้ง (`portrait`) ทุกหน้า
- ไอคอนแอป (`ic_launcher_background` / `ic_launcher_foreground`) ต้องมีอยู่ใน `res/drawable/` ไม่เช่นนั้น build จะ error ตอน link resource
- ใช้ `androidx.appcompat` และธีม `Theme.AppCompat.Light.NoActionBar`

---

## เครดิต

โปรเจกต์ส่งงานวิชาเขียนโปรแกรม โครงสร้าง OOP + MVC ออกแบบตามเอกสาร UI Design 10 หน้าจอ
