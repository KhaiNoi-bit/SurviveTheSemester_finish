package com.example.survivethesemester.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Event {

    public static class Option {
        public final String label, hint;
        public final int study, energy, happy, stress, money;
        public int subject = 0;                       // ผลต่อวิชาของ event (ถ้ามี)
        public int failChance = 0, failStudy = 0, failSubject = 0; // โอกาสพลาด
        public int actionCost = 0;                    // เสีย action

        public Option(String label, String hint, int study, int energy, int happy, int stress, int money) {
            this.label = label; this.hint = hint;
            this.study = study; this.energy = energy; this.happy = happy; this.stress = stress; this.money = money;
        }
        public Option subj(int d) { subject = d; return this; }
        public Option risk(int chance, int failStudy, int failSubject) {
            this.failChance = chance; this.failStudy = failStudy; this.failSubject = failSubject; return this;
        }
        public Option cost(int n) { actionCost = n; return this; }
    }

    public final String emoji, title, description;
    public final List<Option> options = new ArrayList<>();
    public int subjectIndex = -1;

    public Event(String emoji, String title, String description) {
        this.emoji = emoji; this.title = title; this.description = description;
    }

    // ---------- Event สุ่ม ----------
    public static Event random(Random r) {
        List<Event> pool = new ArrayList<>(Arrays.asList(
                friendDinner(), rainDelay(), laptopCrash(), groupProject(), campusFestival(), roommateNoise(), examWarning(), scholarshipDeal()
        ));
        return pool.get(r.nextInt(pool.size()));
    }

    public static Event friendDinner() {
        Event e = new Event("🍜", "เพื่อนชวนไปทานข้าว", "เพื่อนชวนไปกินมื้อยาวแบบไม่อายใคร อาหารอร่อย แต่ต้องตัดสินใจเร็ว");
        e.options.add(new Option("ไปร่วมกัน", "😊 +18 / 💰 -120 / 😰 +6", 0, -6, 18, 6, -120));
        e.options.add(new Option("ปฏิเสธและกลับห้อง", "📚 +8 / ⚡ -4", 8, -4, 0, 0, 0));
        e.options.add(new Option("ชวนเพื่อนมาคุยที่ห้อง", "😊 +10 / 💰 0", 4, -4, 10, 0, 0));
        return e;
    }

    public static Event rainDelay() {
        Event e = new Event("🌧️", "ฝนตกหนักก่อนเข้าเรียน", "ถนนลื่นและรถติด กว่าจะถึงคณะก็ต้องใช้เวลาเยอะ");
        e.options.add(new Option("รอรถเมล์", "เสียเวลา 1 Action", 0, 0, 0, 0, 0).cost(1));
        e.options.add(new Option("วิ่งไปเรียน", "⚡ -14 / 😰 +8", 0, -14, 0, 8, 0));
        e.options.add(new Option("ขอยืมรถเพื่อน", "💰 -40 / 😊 +4", 0, -4, 4, 0, -40));
        return e;
    }

    public static Event laptopCrash() {
        Event e = new Event("💻", "คอมพิวเตอร์ค้าง", "ไฟล์งานที่ต้องส่งหายตอน Save สุดท้าย");
        e.options.add(new Option("โทรซ่อมด่วน", "💰 -180 / ⚡ -8", 0, -8, 0, 0, -180));
        e.options.add(new Option("ขอเพื่อนช่วย", "😊 -4 / 📚 +6", 6, -4, -4, 0, 0));
        e.options.add(new Option("ลาและเริ่มใหม่", "📚 -12 / 😰 +10", -12, -6, 0, 10, 0));
        return e;
    }

    public static Event groupProject() {
        Event e = new Event("🤝", "เพื่อนขอร่วมงานกลุ่ม", "มีคนเข้ามาเสนอให้ร่วมทำ project ทันที");
        e.options.add(new Option("ร่วมกันทำ", "📚 +12 / 😊 +8 / ⚡ -10", 12, -10, 8, 2, 0));
        e.options.add(new Option("ทำคนเดียวแทน", "📚 +8 / 😰 +12", 8, -8, 0, 12, 0));
        e.options.add(new Option("ปฏิเสธก่อน", "😊 -6 / 😰 -4", 0, 0, -6, -4, 0));
        return e;
    }

    public static Event campusFestival() {
        Event e = new Event("🎉", "งานค่ายมหาวิทยาลัย", "มีบูธอาหารและกิจกรรมเปิดตลาด ทำให้อยากร่วมสนุก");
        e.options.add(new Option("ไปเที่ยวสักชั่วโมง", "😊 +16 / ⚡ -12 / 💰 -80", 0, -12, 16, 0, -80));
        e.options.add(new Option("ช่วยจัดงาน", "😊 +10 / 📚 +4 / 😰 +5", 4, -6, 10, 5, 0));
        e.options.add(new Option("เว้นไว้ก่อน", "📚 +6 / 😰 -2", 6, 0, 0, -2, 0));
        return e;
    }

    public static Event roommateNoise() {
        Event e = new Event("🏠", "เพื่อนบ้านดังมาก", "เสียงดังรบกวนจนคุณแทบไม่ทำงานได้");
        e.options.add(new Option("ไปคุยปรับความเข้าใจ", "😊 +8 / 😰 -8", 0, 0, 8, -8, 0));
        e.options.add(new Option("ปิดประตูและทำงานต่อ", "📚 +5 / ⚡ -8", 5, -8, 0, 4, 0));
        e.options.add(new Option("ออกไปชิลที่ร้านกาแฟ", "😊 +12 / 💰 -70", 0, -4, 12, 2, -70));
        return e;
    }

    public static Event examWarning() {
        Event e = new Event("📣", "อาจารย์แจ้งล่วงหน้า", "มีการคาดการณ์ว่ามีสอบย่อยในช่วงถัดไป");
        e.options.add(new Option("ทบทวนก่อนสอบ", "📚 +14 / ⚡ -10", 14, -10, 0, 6, 0));
        e.options.add(new Option("อ่านแค่สรุปสั้น", "📚 +8 / 😰 +8", 8, -6, 0, 8, 0));
        e.options.add(new Option("เลี่ยงและทำอย่างอื่น", "😊 +6 / 📚 -10", -10, -4, 6, 0, 0));
        return e;
    }

    public static Event scholarshipDeal() {
        Event e = new Event("🏆", "ทุนกิจกรรมเปิด", "มีโอกาสรับทุนหรือประสบการณ์ bonus ถ้าทำงานให้ครบ");
        e.options.add(new Option("สมัครทันที", "💰 +120 / ⚡ -8 / 😰 +6", 4, -8, 0, 6, 120));
        e.options.add(new Option("ใช้เวลาเตรียมเอกสาร", "📚 +10 / ⚡ -12", 10, -12, 0, 6, 0));
        e.options.add(new Option("ไม่สนใจ", "😊 +2", 0, 0, 2, 0, 0));
        return e;
    }

    // ---------- Assignment ----------
    public static Event assignment(int subjectIndex, String subjectName) {
        String[] titles = {
                "Assignment ส่งด่วน!",
                "Quiz รออยู่ด้านหน้า",
                "Project ถึงกำหนดส่ง",
                "Lab Report ต้องส่งก่อนเที่ยง",
                "Presentation เตรียมพร้อม"
        };
        String[] emojis = {"🚨", "🧠", "📚", "📝", "🎤"};
        Random r = new Random();
        String emoji = emojis[r.nextInt(emojis.length)];
        String title = titles[r.nextInt(titles.length)];
        Event e = new Event(emoji, title, "วิชา " + subjectName + " มีภารกิจที่ต้องจัดการให้ทัน");
        e.subjectIndex = subjectIndex;
        e.options.add(new Option("ทำทันที", "📚 +14 / ⚡ -18 / 😰 +8", 14, -18, 0, 8, 0).subj(12));
        e.options.add(new Option("แบ่งเวลาทำ", "📚 +10 / ⚡ -10 / มีโอกาสลืม", 10, -10, 0, 6, 0).subj(8).risk(35, -8, -8));
        e.options.add(new Option("เลื่อนส่ง", "📚 -10 / 😰 +12", -10, -4, 0, 12, 0).subj(-14));
        return e;
    }
}
