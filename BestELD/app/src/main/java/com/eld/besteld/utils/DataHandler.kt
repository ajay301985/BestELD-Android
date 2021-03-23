package com.eld.besteld.utils

import androidx.lifecycle.ViewModelProvider
import com.eld.besteld.roomDataBase.DayData
import com.eld.besteld.roomDataBase.DriverInformation
import com.eld.besteld.roomDataBase.LogDataViewModel
import kotlinx.android.synthetic.main.duty_inspection_layout.*
import java.sql.Driver
import java.time.LocalDateTime
import java.util.*


val TEST_DRIVER_DL_NUMBER = "xyz12345"
val DRIVING_MODE_SPEED = 5.0

internal object DataHandler {
    private var instance: DataHandler? = null

    lateinit var currentDriver: DriverInformation
    var currentDayData: DayData? = null
    lateinit var currentDutyStatus: DutyStatus
    lateinit var logDataViewModel: LogDataViewModel

    init {
       // currentDriver = DataHandler.currentDriver
        println("Singleton class invoked.")
        currentDutyStatus = DutyStatus.OFFDUTY
    }

    fun createDayData(start: LocalDateTime, end: LocalDateTime, status: DutyStatus, desciption: String?, driver: DriverInformation): DayData {
        val startLatitude = 12312321.777// LocationHandler.locationLatitude()
        var startLongitude = 777777.777//LocationHandler.locationLongitude()
        //var cityName = LocationHandler.cityName()
        var dayDataObj = DayData(id_DayData = 0,//TimeUtility.currentDateUTC().toString(),
            startLatitude = startLatitude,
            startLongitude = startLongitude,
            rideDesciption = desciption ?: "",
            startTime = start.toString(),
            endTime = end.toString(),
            dutyStatus = status.rawValue,
            dlNumber = driver.dlNumber ?:  TEST_DRIVER_DL_NUMBER,
            day = TimeUtility.getCurrentDateTimeInterval()
        )

        return dayDataObj
    }


    fun dutyStatusChanged(status: DutyStatus, description: String?, timeToStart: LocalDateTime? = null) {
        if (status == currentDutyStatus) {
            return //same status
        }

        when (status) {
            DutyStatus.ONDUTY-> {
                performDutyStatusChanged(description,timeToStart,DutyStatus.ONDUTY)
            }
            DutyStatus.OFFDUTY-> {
                performDutyStatusChanged(description,timeToStart,DutyStatus.OFFDUTY) //TODO:Rahul Fix it
            }
            DutyStatus.SLEEPER-> {
                performDutyStatusChanged(description,timeToStart,DutyStatus.SLEEPER)
            }
            DutyStatus.YARD-> {
                performDutyStatusChanged(description,timeToStart,DutyStatus.YARD)
            }
            DutyStatus.DRIVING-> {
                performDutyStatusChanged(description,timeToStart,DutyStatus.DRIVING)
            }
        }
    }



    fun performDutyStatusChanged(description: String?, startTime: LocalDateTime? = null, dutyStatus: DutyStatus) {
//        if (currentDayData == null) {
//            return //Invalid data
//        }

        if (currentDayData != null) {
            currentDayData!!.endTime = startTime.toString() ?: Date().toString()
            currentDayData!!.endTimeString = startTime.toString() ?: Date().toString()
            logDataViewModel.updateDayData(currentDayData!!)
        }

        val currentDayData1 = createDayData(startTime ?: TimeUtility.currentDateUTC(),TimeUtility.currentDateUTC(),dutyStatus,description,currentDriver)
//        logDataViewModel = ViewModelProvider(this).get(LogDataViewModel::class.java)
        logDataViewModel.insertDayDataForDayMetaData(currentDayData1,Date(),DataHandler.currentDriver.dlNumber)
        currentDayData = currentDayData1
        /*
            let eldDataRecord = EldDeviceManager.shared.currentEldDataRecord
    let preDutyStatus = DutyStatus(rawValue: dayData.dutyStatus ?? "OFFDUTY")
    if ((eldDataRecord != nil) && (preDutyStatus == DutyStatus.DRIVING || preDutyStatus == DutyStatus.ONDUTY)) {
      dayData.endOdometer = eldDataRecord?.odometer ?? 0
    }
         */
    }


    fun performOffDutyStatusChanged(description: String?, startTime: Date? = null) {
        //TODO: Rahul Need to implement
    }

    fun getTestDriver(): DriverInformation {
        //TODO: check in database of Test driver is availble
        //if avaialbe return
        //else createTestDriverData
        // save database
        return  createTestDriverData()
    }

    fun createTestDriverData(): DriverInformation {
        return DriverInformation(
            dlNumber = TEST_DRIVER_DL_NUMBER,
            firstName = "Test",
            lastName = "Driver"
        )
    }
    /*
      func createTestDriverData() -> Driver? {
    let context = BLDAppUtility.dataContext()

    let entity = NSEntityDescription.entity(forEntityName: "Driver", in: context)
    let testDriver = NSManagedObject(entity: entity!, insertInto: context)

    guard testDriver != nil, testDriver is Driver else {
      print("wrong object")
      return nil
    }
    testDriver.setValue(TEST_DRIVER_DL_NUMBER, forKey: "dlNumber")
    testDriver.setValue("test driver", forKey: "firstName")

    do {
      try context.save()
    } catch {
      print("Failed to save driver data\(error)")
    }
    return testDriver as? Driver
  }
     */
/*

  private func performOffDutyStatusChanged(description: String?, startTime: Date? = nil) {
    let driverMetaData = DataHandeler.shared.dayMetaData(dayStart: BLDAppUtility.startOfTheDayTimeInterval(for: Date()), driverDL: currentDriver.dlNumber ?? TEST_DRIVER_DL_NUMBER, createOnDemand: true)
    guard let metaData = driverMetaData, metaData.dayData?.count ?? 0 > 0 else {
      let startDate = Date().startOfDayWithTimezone // enter a test object
      currentDayData = DataHandeler.shared.createDayData(start: startDate, end: Date(), status: .OFFDUTY, desciption: description ?? "off duty", for: currentDriver)
      if UserPreferences.shared.shouldSyncDataToServer {
        DailyLogRepository.shared.sendDailyLogsToServer(fromDate: Date(), numberOfDays: 1) { result in
          let dayMetaDataObj = DataHandeler.shared.dayMetaData(dayStart: BLDAppUtility.startOfTheDayTimeInterval(for: Date()), driverDL: self.currentDriver.dlNumber ?? TEST_DRIVER_DL_NUMBER)
          if dayMetaDataObj != nil {
            if let dayDataArr = dayMetaDataObj?.dayData?.allObjects as? [DayData], dayDataArr.count > 0 {
              let sortedData = dayDataArr.sorted(by: { $0.startTime ?? Date() < $1.startTime ?? Date() })
              let latestDayData = sortedData.last
              self.currentDayData = latestDayData
              NotificationCenter.default.post(NSNotification(name: NSNotification.Name(rawValue: "DatabaseDidChanged"), object: self) as Notification)
            }
          }
        }
      } else {
        NotificationCenter.default.post(NSNotification(name: NSNotification.Name(rawValue: "DatabaseDidChanged"), object: self) as Notification)
      }
      return
    }

    guard let dayData = currentDayData else {
      print("invalid day data")
      return
    }

    dayData.endTime = startTime ?? Date()
    dayData.endTimeString = startTime?.description ?? Date().description

    let eldDataRecord = EldDeviceManager.shared.currentEldDataRecord
    let preDutyStatus = DutyStatus(rawValue: dayData.dutyStatus ?? "OFFDUTY")
    if ((eldDataRecord != nil) && (preDutyStatus == DutyStatus.DRIVING || preDutyStatus == DutyStatus.ONDUTY)) {
      dayData.endOdometer = eldDataRecord?.odometer ?? 0
    }

    performDutyStatusChanged(description: description, startTime: startTime, dutyStatus: .OFFDUTY)
  }
private func performDutyStatusChanged(description: String?, startTime: Date? = nil, dutyStatus: DutyStatus) {
    guard let dayData = currentDayData else {
      print("invalid day data")
      return
    }

    let currentTime = Date()
    dayData.endTime = startTime ?? Date()
    dayData.endTimeString = startTime?.description ?? Date().description
    let eldDataRecord = EldDeviceManager.shared.currentEldDataRecord
    let preDutyStatus = DutyStatus(rawValue: dayData.dutyStatus ?? "OFFDUTY")
    if ((eldDataRecord != nil) && (preDutyStatus == DutyStatus.DRIVING || preDutyStatus == DutyStatus.ONDUTY)) {
      dayData.endOdometer = eldDataRecord?.odometer ?? 0
    }

    let currentDayData1 = DataHandeler.shared.createDayData(start: startTime ?? currentTime, end: Date(), status: dutyStatus, desciption: description ?? "", for: currentDriver)
    currentDayData = currentDayData1
    if UserPreferences.shared.shouldSyncDataToServer {
      DailyLogRepository.shared.sendDailyLogsToServer(fromDate: Date(), numberOfDays: 1) { result in
        let dayMetaDataObj = DataHandeler.shared.dayMetaData(dayStart: BLDAppUtility.startOfTheDayTimeInterval(for: Date()), driverDL: self.currentDriver.dlNumber ?? TEST_DRIVER_DL_NUMBER)
        if dayMetaDataObj != nil {
          if let dayDataArr = dayMetaDataObj?.dayData?.allObjects as? [DayData], dayDataArr.count > 0 {
            let sortedData = dayDataArr.sorted(by: { $0.startTime ?? Date() < $1.startTime ?? Date() })
            let latestDayData = sortedData.last
            self.currentDayData = latestDayData
            NotificationCenter.default.post(NSNotification(name: NSNotification.Name(rawValue: "DatabaseDidChanged"), object: self) as Notification)
          }
        }
      }
    } else {
      NotificationCenter.default.post(NSNotification(name: NSNotification.Name(rawValue: "DatabaseDidChanged"), object: self) as Notification)
    }
  }

func dutyStatusChanged(status: DutyStatus, description: String? = nil, timeToStart: Date? = nil) {
    let dutyStatus = DutyStatus(rawValue: currentDayData?.dutyStatus ?? "OFFDUTY")
    if currentDayData != nil, dutyStatus == status {
      print("Status is same")
      return
    }

    switch status {
      case .ONDUTY:
        performDutyStatusChanged(description: description, startTime: timeToStart, dutyStatus: .ONDUTY)
      case .OFFDUTY:
        performOffDutyStatusChanged(description: description, startTime: timeToStart)
      case .SLEEPER:
        performDutyStatusChanged(description: description, startTime: timeToStart, dutyStatus: .SLEEPER)
      case .YARD:
        performDutyStatusChanged(description: description, startTime: timeToStart, dutyStatus: .YARD)
      case .DRIVING:
        performDutyStatusChanged(description: description, startTime: timeToStart, dutyStatus: .DRIVING)
      default:
        performDutyStatusChanged(description: description, startTime: timeToStart, dutyStatus: .PERSONAL)
    }

    //let dayMetaDataObj = dayMetaData(dayStart: BLDAppUtility.startOfTheDayTimeInterval(for: Date()), driverDL: currentDriver.dlNumber ?? TEST_DRIVER_DL_NUMBER, createOnDemand: true)
    //let dayDataArr = dayMetaDataObj?.dayData?.allObjects as! [DayData]
   // let sortedData = dayDataArr.sorted(by: { $0.startTime ?? Date() < $1.startTime ?? Date() })
  //  GraphGenerator.shared.generatePath(dayDataArr: sortedData)
  }

  private func performOffDutyStatusChanged(description: String?, startTime: Date? = nil) {
    let driverMetaData = DataHandeler.shared.dayMetaData(dayStart: BLDAppUtility.startOfTheDayTimeInterval(for: Date()), driverDL: currentDriver.dlNumber ?? TEST_DRIVER_DL_NUMBER, createOnDemand: true)
    guard let metaData = driverMetaData, metaData.dayData?.count ?? 0 > 0 else {
      let startDate = Date().startOfDayWithTimezone // enter a test object
      currentDayData = DataHandeler.shared.createDayData(start: startDate, end: Date(), status: .OFFDUTY, desciption: description ?? "off duty", for: currentDriver)
      if UserPreferences.shared.shouldSyncDataToServer {
        DailyLogRepository.shared.sendDailyLogsToServer(fromDate: Date(), numberOfDays: 1) { result in
          let dayMetaDataObj = DataHandeler.shared.dayMetaData(dayStart: BLDAppUtility.startOfTheDayTimeInterval(for: Date()), driverDL: self.currentDriver.dlNumber ?? TEST_DRIVER_DL_NUMBER)
          if dayMetaDataObj != nil {
            if let dayDataArr = dayMetaDataObj?.dayData?.allObjects as? [DayData], dayDataArr.count > 0 {
              let sortedData = dayDataArr.sorted(by: { $0.startTime ?? Date() < $1.startTime ?? Date() })
              let latestDayData = sortedData.last
              self.currentDayData = latestDayData
              NotificationCenter.default.post(NSNotification(name: NSNotification.Name(rawValue: "DatabaseDidChanged"), object: self) as Notification)
            }
          }
        }
      } else {
        NotificationCenter.default.post(NSNotification(name: NSNotification.Name(rawValue: "DatabaseDidChanged"), object: self) as Notification)
      }
      return
    }

    guard let dayData = currentDayData else {
      print("invalid day data")
      return
    }

    dayData.endTime = startTime ?? Date()
    dayData.endTimeString = startTime?.description ?? Date().description

    let eldDataRecord = EldDeviceManager.shared.currentEldDataRecord
    let preDutyStatus = DutyStatus(rawValue: dayData.dutyStatus ?? "OFFDUTY")
    if ((eldDataRecord != nil) && (preDutyStatus == DutyStatus.DRIVING || preDutyStatus == DutyStatus.ONDUTY)) {
      dayData.endOdometer = eldDataRecord?.odometer ?? 0
    }

    performDutyStatusChanged(description: description, startTime: startTime, dutyStatus: .OFFDUTY)
  }

  private func performDutyStatusChanged(description: String?, startTime: Date? = nil, dutyStatus: DutyStatus) {
    guard let dayData = currentDayData else {
      print("invalid day data")
      return
    }

    let currentTime = Date()
    dayData.endTime = startTime ?? Date()
    dayData.endTimeString = startTime?.description ?? Date().description
    let eldDataRecord = EldDeviceManager.shared.currentEldDataRecord
    let preDutyStatus = DutyStatus(rawValue: dayData.dutyStatus ?? "OFFDUTY")
    if ((eldDataRecord != nil) && (preDutyStatus == DutyStatus.DRIVING || preDutyStatus == DutyStatus.ONDUTY)) {
      dayData.endOdometer = eldDataRecord?.odometer ?? 0
    }

    let currentDayData1 = DataHandeler.shared.createDayData(start: startTime ?? currentTime, end: Date(), status: dutyStatus, desciption: description ?? "", for: currentDriver)
    currentDayData = currentDayData1
    if UserPreferences.shared.shouldSyncDataToServer {
      DailyLogRepository.shared.sendDailyLogsToServer(fromDate: Date(), numberOfDays: 1) { result in
        let dayMetaDataObj = DataHandeler.shared.dayMetaData(dayStart: BLDAppUtility.startOfTheDayTimeInterval(for: Date()), driverDL: self.currentDriver.dlNumber ?? TEST_DRIVER_DL_NUMBER)
        if dayMetaDataObj != nil {
          if let dayDataArr = dayMetaDataObj?.dayData?.allObjects as? [DayData], dayDataArr.count > 0 {
            let sortedData = dayDataArr.sorted(by: { $0.startTime ?? Date() < $1.startTime ?? Date() })
            let latestDayData = sortedData.last
            self.currentDayData = latestDayData
            NotificationCenter.default.post(NSNotification(name: NSNotification.Name(rawValue: "DatabaseDidChanged"), object: self) as Notification)
          }
        }
      }
    } else {
      NotificationCenter.default.post(NSNotification(name: NSNotification.Name(rawValue: "DatabaseDidChanged"), object: self) as Notification)
    }
  }
}



    func createDayData(start: Date, end: Date, status: DutyStatus, desciption: String?, for driver: Driver) -> DayData? {
        let context = BLDAppUtility.dataContext()
        let entity = NSEntityDescription.entity(forEntityName: "DayData", in: context)
        let dayMetaDataObj = NSManagedObject(entity: entity!, insertInto: context)

        dayMetaDataObj.setValue("string", forKey: "day")
        dayMetaDataObj.setValue(driver.dlNumber, forKey: "dlNumber")
        dayMetaDataObj.setValue(status.rawValue, forKey: "dutyStatus")
        dayMetaDataObj.setValue(end, forKey: "endTime")
        dayMetaDataObj.setValue(end.description, forKey: "endTimeString")
        // #warning generate Id
        dayMetaDataObj.setValue("1009", forKey: "id") //
        let currentLocationObj = BldLocationManager.shared.currentLocation
                dayMetaDataObj.setValue(currentLocationObj?.coordinate.latitude, forKey: "startLatitude")
        dayMetaDataObj.setValue(currentLocationObj?.coordinate.longitude, forKey: "startLongitude")
        dayMetaDataObj.setValue(desciption ?? "", forKey: "rideDescription")
        dayMetaDataObj.setValue(start, forKey: "startTime")
        dayMetaDataObj.setValue(start.description, forKey: "startTimeString")
        let userLocation = BldLocationManager.shared.locationText
                dayMetaDataObj.setValue(userLocation, forKey: "startLocation")

        let eldDataRecord = EldDeviceManager.shared.currentEldDataRecord
                if ((eldDataRecord != nil) && (status == DutyStatus.DRIVING || status == DutyStatus.ONDUTY)) {
                    dayMetaDataObj.setValue(eldDataRecord?.odometer ?? 0, forKey: "startOdometer")
                }

        let driverMetaData = dayMetaData(dayStart: BLDAppUtility.startOfTheDayTimeInterval(for: Date()), driverDL: driver.dlNumber ?? TEST_DRIVER_DL_NUMBER)
        if driverMetaData?.dayData?.count ?? 0 < 1 {
            driverMetaData?.setValue(NSSet(object: dayMetaDataObj), forKey: "DayData")
        } else {
            let dayDataArr = driverMetaData?.mutableSetValue(forKey: "DayData")
            dayDataArr?.add(dayMetaDataObj)
        }
        do {
            try context.save()
            } catch {
                print("Failed to save driver data\(error)")
            }

            return dayMetaDataObj as? DayData
        } */

}