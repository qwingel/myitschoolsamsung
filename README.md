# SkyLine
## Введение
SkyLine - идея того, как могло выглядеть и работать мобильное приложения для авиакомпании. **Приложение не предназначено для покупики реальных билетов**!

## Использованные технологии
- Выделенный сервер на Flask(Python)
- SQLite 3.0
- SharedPreferences
- Retrofit 

## Классы и Activity
### MainActivity
Небольшая Activity, которая проверяет подключение к интернету пользователя

```Java
public boolean isConnectingToNetwork(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return !(ni == null);
    }

    public boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            return wifiInfo.getNetworkId() != -1; // Not connected to an access poin
// Connected to an access point
        }
        else return false; // Wi-Fi adapter is OFF
    }
```
и его сессию
```Java
sPref = getSharedPreferences("account", MODE_PRIVATE);
String savedText = sPref.getString("phone", null);
Intent intent;
if(savedText == null) {
    intent = new Intent(getApplicationContext(), Log_in_window.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
} else {
    intent = new Intent(getApplicationContext(), Welcome.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
}
```
Перемещает пользователя в одну из двух активностей:
- Welcome
- Log_in_window

### Welcome
Activity, которая приветсвует пользователя по имени (по указанному в базе данных, если 'Пусто', то приветствует без имени) при запуске приложения. 

### Log_in_window
Активность, сопутствующая регистрации или входу в аккаунт

### LogIn
Activity с *EditText* для ввода номера. Отправляет HTTP запрос на сервер для поиска аккаунта в базе данных с введённым номером. Если не найдёт - перемещает в активность ***Registration***. Иначе - в активность ***ConfirmNumber***.

### Registration
Активность предлагает пользователю ввести пароль и отправляет HTTP запрос на сервер для создания аккаунта с номером из активности LogIn и введённым паролем.

### ConfirmNumber
Активность отправляет на сервер HTTP запрос с введенным кодом, если он совпадает с отправленным, то номер подтвержден и пользователь получает доступ в аккаунт.

### HomeScreen
Активность, в которую мы попадаем после входа в аккаунт или регистрации. 

![HomeScreen](https://github.com/qwingel/myitschoolsamsung/blob/master/pic/HomeScreen.png?raw=true)

Собственно, это наша основная активность. Ищет билеты между городами, учитывая фильтры и даты.

### Basket
Корзина для билетов, здесь пользователь сохраняет понравившиеся билеты. Хранит билеты с помощью ***SharedPreferences***.

### Profile
Активность, которая позволяет изменить личные данные, зная пароль. Выход из аккаунта происходит тоже здесь.

![Profile](https://github.com/qwingel/myitschoolsamsung/blob/master/pic/Profile.png?raw=true)

### RequestToServe
Пожалуй, вся магия происходит именно здесь. 
Класс реализует наши HTTP запросы, которые отправляюся на сервер с помощью ***Retrofit***.


## Сервер
Сервер написан с помощью Flask на Python. Принимает запросы в виде JSON
```JavaScript
{"key", "value"}.
```
Затем обрабатывает и возвращает информацию клиенту.

[Реализация серверной](https://github.com/qwingel/myitschoolsamsung_Server) части находится в отдельном репозитории
