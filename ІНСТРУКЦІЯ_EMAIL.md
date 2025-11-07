# 📧 Налаштування Email для Music System

## ✅ Що вже налаштовано

- ✅ Email одержувача: `vladisalvpro100igrok@gmail.com`
- ✅ Email відправника: `vladisalvpro100igrok@gmail.com`
- ✅ SMTP сервер: Gmail (smtp.gmail.com:587)
- ⚠️ **ПОТРІБНО:** App Password

---

## 🔑 Як Отримати App Password (5 хвилин)

### Крок 1: Увімкніть 2-Step Verification

1. Відкрийте: https://myaccount.google.com/security
2. Знайдіть розділ **"2-Step Verification"**
3. Якщо вимкнено - натисніть **"Get started"** і увімкніть
4. Пройдіть процес налаштування (додайте номер телефону)

### Крок 2: Згенеруйте App Password

1. Відкрийте: https://myaccount.google.com/apppasswords
2. Увійдіть у акаунт: `vladisalvpro100igrok@gmail.com`
3. У полі **"Select app"** оберіть → **"Other (Custom name)"**
4. Введіть: **"Music System Logging"**
5. Натисніть **"Generate"**
6. **Скопіюйте** згенерований 16-символьний пароль

   Виглядає так: `abcd efgh ijkl mnop`

### Крок 3: Додайте в Конфігурацію

1. Відкрийте файл: `resources/logging.properties`
2. Знайдіть рядок:
   ```properties
   log.email.smtp.password=ВСТАВТЕ_ТУТ_APP_PASSWORD_З_GOOGLE
   ```
3. Замініть на ваш app password (без пробілів):
   ```properties
   log.email.smtp.password=abcdefghijklmnop
   ```
4. **Збережіть файл**

---

## ✅ Перевірка Налаштувань

Після налаштування ваш файл `resources/logging.properties` має виглядати так:

```properties
log.email.on.fatal=true
log.email.to=vladisalvpro100igrok@gmail.com
log.email.from=vladisalvpro100igrok@gmail.com
log.email.smtp.host=smtp.gmail.com
log.email.smtp.port=587
log.email.smtp.user=vladisalvpro100igrok@gmail.com
log.email.smtp.password=ваш-app-password-тут
```

---

## 🧪 Тест Email Розсилки

### Варіант 1: Швидкий тест

```bash
cd /Users/admin/univercity/labs/lab_5
javac -d bin -encoding UTF-8 -sourcepath src src/com/musicsystem/TestEmailAndLogging.java
java -cp bin com.musicsystem.TestEmailAndLogging
```

### Варіант 2: Повний тест

```bash
./test_all.sh
```

### Що очікувати:

✅ **Якщо все працює:**
```
✓ Email з критичною помилкою відправлено на: vladisalvpro100igrok@gmail.com
```

❌ **Якщо JavaMail не встановлено:**
```
⚠️  JavaMail бібліотека не знайдена. Email не відправлено.
```
(Це нормально - логи все одно працюють!)

❌ **Якщо неправильний пароль:**
```
Помилка відправки email: Authentication failed
```
→ Перевірте app password

---

## 📦 Встановлення JavaMail (Опціонально)

Якщо хочете реально відправляти email:

### Спосіб 1: Завантажити вручну

1. Завантажте: https://github.com/javaee/javamail/releases
2. Скачайте `javax.mail.jar`
3. Помістіть в папку `libs/`
4. При компіляції додайте до classpath:
   ```bash
   javac -cp libs/javax.mail.jar -d bin -sourcepath src src/com/musicsystem/Main.java
   java -cp bin:libs/javax.mail.jar com.musicsystem.Main
   ```

### Спосіб 2: Без JavaMail

Програма працюватиме без email - логи зберігатимуться тільки у файл `logs/application.log`

---

## 🔒 БЕЗПЕКА

### ⚠️ ВАЖЛИВО!

**НЕ публікуйте файл `logging.properties` з паролем у Git!**

### Додайте до .gitignore:

```bash
echo "resources/logging.properties" >> .gitignore
echo "src/logging.properties" >> .gitignore
```

### Створіть шаблон:

Створіть `logging.properties.template` БЕЗ пароля:
```properties
log.email.smtp.user=your-email@gmail.com
log.email.smtp.password=YOUR_APP_PASSWORD_HERE
```

---

## ❓ Поширені Проблеми

### "Authentication failed"
→ Неправильний app password або не увімкнена 2-Step Verification

### "JavaMail бібліотека не знайдена"
→ Нормально! Програма працює без email. Логи зберігаються у файл.

### "Connection timed out"
→ Перевірте інтернет з'єднання або firewall

### Email не приходить
→ Перевірте папку "Spam" у вашій пошті

---

## 📞 Швидка Довідка

| Параметр | Значення |
|----------|----------|
| Email | vladisalvpro100igrok@gmail.com |
| SMTP сервер | smtp.gmail.com |
| Порт | 587 |
| App Password | Згенеруйте на myaccount.google.com/apppasswords |

---

## ✨ Готово!

Після налаштування app password:
1. ✅ Логи пишуться у файл: `logs/application.log`
2. ✅ Критичні помилки (FATAL) відправляються на email
3. ✅ Все працює автоматично

**Успіхів! 🚀**

