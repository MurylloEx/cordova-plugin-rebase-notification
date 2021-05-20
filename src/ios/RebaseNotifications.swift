/*
static func main ()
{
  let notCenter = UNUserNotificationCenter.current ()
  let options : UNAuthorizationOptions = [.alert, .sound, .badge]
  notCenter.requestAuthorization (options: options)
  {
    (success, error)
    in if error = = nil {
      if success = = true {
        //O usuário aceitou a permissão de notificação.
        let content = UNMutableNotificationContent ()
        content.title = "Titulo da notificação" 
        content.body = "Corpo da notificação" 
        content.sound = UNNotificationSound.default 
        content.badge = 1 
        
        let date = Date (timeIntervalSinceNow: 5)
        let triggerDate = Calendar.current.dateComponents (
          [.year, .month, .day, .hour, .minute, .second],
          from : date
        )
        let trigger = UNCalendarNotificationTrigger (dateMatching: triggerDate, repeats: false)
        let request = UNNotificationRequest (
          identifier : "notifyId",
          content : content,
          trigger : trigger
        )
        notCenter.add (request)
        {
          (error)
          in if error ! = nil {
            //O agendamento da notificação falhou.
            //Obtenha mais informações em error.localizedDescription
          }
        }
      }
      else {
        //O usuário rejeitou a permissão de notificação.
      }
    }
    else {
      //Retorna o erro ao plugin do cordova.
    }
  }
}
*/